package com.projet.chatmanagement.controller;

import com.projet.chatmanagement.dto.chat.ChatCreateDTO;
import com.projet.chatmanagement.dto.chat.ChatPreview;
import com.projet.chatmanagement.entity.Chat;
import com.projet.chatmanagement.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }
    @PostMapping
    public ResponseEntity<Chat> createChat(@Valid @RequestBody ChatCreateDTO chatDTO) {
        List<Long> memberIds = (chatDTO.getMemberIds() != null) ? chatDTO.getMemberIds() : new ArrayList<>();
        Chat createdChat = chatService.createChat(chatDTO.getName(), memberIds);
        // Send WebSocket message to notify clients about the new chat creation
        messagingTemplate.convertAndSend("/topic/chats", createdChat);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable Long chatId) {
        return chatService.getChat(chatId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable Long chatId) {
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            // Assuming RuntimeException is thrown when the chat is not found.
            // It's better to use a more specific exception if your application defines one.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No chat with id " + chatId);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatPreview>> getAllChatsForUser(@PathVariable Long userId,@RequestHeader("Authorization") String token) {
        System.out.println("test2");
        return ResponseEntity.ok(chatService.getAllChatsForUser(userId, token));
    }
}
