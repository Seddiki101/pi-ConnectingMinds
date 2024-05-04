package com.projet.chatmanagement.serviceImpl;

import com.projet.chatmanagement.client.UserClient;
import com.projet.chatmanagement.dao.ChatDao;
import com.projet.chatmanagement.dto.chat.ChatPreview;
import com.projet.chatmanagement.dto.user.UserDTO;
import com.projet.chatmanagement.entity.Chat;
import com.projet.chatmanagement.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatDao chatDao;

    @Resource
    private UserManagementClient userManagementClient;
    private UserClient userClient;



    @Override
    @Transactional
    public Chat createChat(String name, List<Long> memberIds) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setMemberIds(new HashSet<>(memberIds)); // Using HashSet to store unique user IDs
        return chatDao.save(chat);
    }
    @Transactional
    public Chat getOrCreateChat(Long userId1, Long userId2) {
        // First, try to find an existing chat with exactly these two users
        Optional<Chat> existingChat = chatDao.findChatByUserIds(userId1, userId2);

        if (existingChat.isPresent()) {
            return existingChat.get();
        } else {
            // No chat found, create a new one
            Chat newChat = new Chat();
            Set<Long> memberIds = new HashSet<>();
            memberIds.add(userId1);
            memberIds.add(userId2);
            newChat.setMemberIds(memberIds);
            return chatDao.save(newChat);
        }
    }

    @Override
    public Optional<Chat> getChat(Long chatId) {
        System.out.print("here");
        return chatDao.findById(chatId);
    }

    @Override
    @Transactional
    public void deleteChat(Long chatId) {
        int deletedCount = chatDao.deleteByChatId(chatId);
        if (deletedCount == 0) {
            throw new RuntimeException("No chat found with ID: " + chatId);
        }
        System.out.println("Deleted chat with ID: " + chatId);
    }
    @Override
    public List<ChatPreview> getAllChatsForUser(Long userId, String token) {
        List<Object[]> results = chatDao.findChatDataByUserId(userId);
        return results.stream()
                .map(result -> {
//                    UserDTO user = userManagementClient.fetchUserById((Long) result[2], token);
                    UserDTO user = userClient.findUserById((Long) result[2]);
                    System.out.println(user.toString());
                    String fullName = user.getFirstName() + " " + user.getLastName();

                    return new ChatPreview((Long) result[0], fullName, user, (String) result[3], (Timestamp) result[4]);
                })
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public Chat addMemberToChat(Long chatId, Long userId) {
        Chat chat = chatDao.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        chat.getMemberIds().add(userId);
        return chatDao.save(chat);
    }

    @Override
    @Transactional
    public Chat removeMemberFromChat(Long chatId, Long userId) {
        Chat chat = chatDao.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        chat.getMemberIds().remove(userId);
        return chatDao.save(chat);
    }
}
