package com.projet.chatmanagement.service;

import com.projet.chatmanagement.dto.chat.ChatPreview;
import com.projet.chatmanagement.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Chat createChat(String name, List<Long> memberIds);
    Optional<Chat> getChat(Long chatId);
    public Chat getOrCreateChat(Long senderId, Long receiverId);
    void deleteChat(Long chatId);
    List<ChatPreview> getAllChatsForUser(Long userId, String token);
    Chat addMemberToChat(Long chatId, Long userId);
    Chat removeMemberFromChat(Long chatId, Long userId);
}
