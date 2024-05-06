package com.projet.chatmanagement.serviceImpl;

import com.projet.chatmanagement.dao.ChatDao;
import com.projet.chatmanagement.dao.MessageDao;
import com.projet.chatmanagement.dto.message.MessageCreateDTO;
import com.projet.chatmanagement.entity.Chat;
import com.projet.chatmanagement.entity.Message;
import com.projet.chatmanagement.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private WebSocketService webSocketService;
    @Resource
    private MessageDao messageDao;

    @Resource
    private ChatDao chatDao;

    @Override
    @Async
    @Transactional
    public CompletableFuture<Message> sendMessage(MessageCreateDTO messageDTO) {
        Message message = convertToMessage(messageDTO);
        Message savedMessage = messageDao.save(message);
        Long chatId = savedMessage.getChat().getChatId();
        String topic = "chat" + chatId.toString();
        Long recipientId = getRecipientId(savedMessage);
        String userPreviewTopic = "user" + recipientId;
        String thisuserPreeviwTopic = "user" + message.getUserId();
        webSocketService.notifyFrontend(topic);
        webSocketService.notifyFrontend(userPreviewTopic);  // Notify user-preview topic
        webSocketService.notifyFrontend(thisuserPreeviwTopic);
        return CompletableFuture.completedFuture(savedMessage);
    }

    @Override
    @Transactional
    public Message editMessage(Long messageId, String newContent) {
        Message message = messageDao.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setContent(newContent);
        return messageDao.save(message);
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageDao.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setDeleted(true);
        message.setContent("This message was deleted");
        messageDao.save(message);
    }

    @Override
    @Transactional
    public void markMessageAsSeen(Long messageId) {
        Message message = messageDao.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setSeen(true);
        messageDao.save(message);
    }

    @Override
    public List<Message> getAllMessagesForChat(Long chatId) {
        return messageDao.findByChatId(chatId);
    }

    private Message convertToMessage(MessageCreateDTO dto) {
        Message message = new Message();
        message.setContent(dto.getContent());


        Chat chat = chatDao.findById(dto.getChatId()).orElseThrow(() ->
                new IllegalArgumentException("No chat found with ID: " + dto.getChatId()));

        message.setUserId(dto.getUserId());
        message.setChat(chat);
        return message;
    }

    private Long getRecipientId(Message message) {
        Set<Long> memberIds = message.getChat().getMemberIds();
        if (memberIds.size() != 2) {
            // Handle the case where the set has a different size (optional)
            // You can throw an exception, return a default value, etc.
            return null;  // Or throw an exception
        }

        for (Long memberId : memberIds) {
            if (!Objects.equals(memberId, message.getUserId())) {
                return memberId;
            }
        }

        // This shouldn't be reached if there are exactly 2 members
        return null;  // Or throw an exception for unexpected case
    }
}