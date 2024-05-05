package com.projet.chatmanagement.controller;

import com.projet.chatmanagement.dto.message.MessageCreateDTO;
import com.projet.chatmanagement.dto.message.MessageUpdateDTO;
import com.projet.chatmanagement.dto.message.NewMessageDTO;
import com.projet.chatmanagement.entity.Chat;
import com.projet.chatmanagement.entity.Message;
import com.projet.chatmanagement.service.ChatService;
import com.projet.chatmanagement.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<MessageCreateDTO>> sendMessage(@Valid @RequestBody MessageCreateDTO messageDTO) {
        messageDTO.setTimestamp(LocalDateTime.now());

        return getResponseEntityCompletableFuture(messageDTO);
    }

    @PostMapping("/new")
    public CompletableFuture<ResponseEntity<MessageCreateDTO>> newMessage(@Valid @RequestBody NewMessageDTO newMessage) {

        Chat chat = chatService.getOrCreateChat(newMessage.getSenderId(), newMessage.getRecieverId());
        MessageCreateDTO messageDTO = new MessageCreateDTO();
        messageDTO.setContent(newMessage.getContent());
        messageDTO.setUserId(newMessage.getSenderId());
        messageDTO.setChatId(chat.getChatId());
        return getResponseEntityCompletableFuture(messageDTO);
    }

    private CompletableFuture<ResponseEntity<MessageCreateDTO>> getResponseEntityCompletableFuture(MessageCreateDTO messageDTO) {
        return messageService.sendMessage(messageDTO)
                .thenApply(message -> {
                    MessageCreateDTO responseDTO = new MessageCreateDTO();
                    responseDTO.setContent(message.getContent());
                    responseDTO.setChatId(message.getChat().getChatId());
                    responseDTO.setUserId(message.getUserId());
                    responseDTO.setTimestamp(message.getTimestamp()); // Pass the timestamp to the DTO

                    // Send WebSocket message to notify clients about the new message
                    messagingTemplate.convertAndSend("/topic/chat" + message.getChat().getChatId(), responseDTO);

                    return ResponseEntity.ok(responseDTO);
                })
                .exceptionally(exception -> {
                    // Handle exceptions, if any, and return appropriate response
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }


    @PutMapping("/edit")
    public ResponseEntity<Message> editMessage(@Valid @RequestBody MessageUpdateDTO updateDTO) {
        // Extracting messageId and newContent from the DTO
        Long messageId = updateDTO.getMessageId();
        String newContent = updateDTO.getContent();

        // Calling the service method with the extracted values
        Message updatedMessage = messageService.editMessage(messageId, newContent);

        return ResponseEntity.ok(updatedMessage);
    }

    // Other methods remain unchanged
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{messageId}/markAsSeen")
    public ResponseEntity<Void> markMessageAsSeen(@PathVariable Long messageId) {
        messageService.markMessageAsSeen(messageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getAllMessagesForChat(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageService.getAllMessagesForChat(chatId));
    }

    private Message convertToMessage(MessageCreateDTO dto) {
        Message message = new Message();
        message.setContent(dto.getContent());
        return message;
    }
}
