package com.projet.chatmanagement.service;

import com.projet.chatmanagement.dto.message.MessageCreateDTO;
import com.projet.chatmanagement.entity.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface MessageService {
    CompletableFuture<Message> sendMessage(MessageCreateDTO messageDTO); // Updated method
    Message editMessage(Long messageId, String newContent);
    Message deleteMessage(Long messageId);
    void markMessageAsSeen(Long messageId);
    List<Message> getAllMessagesForChat(Long chatId);
}
