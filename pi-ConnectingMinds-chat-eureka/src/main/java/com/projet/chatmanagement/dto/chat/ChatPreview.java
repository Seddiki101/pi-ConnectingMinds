package com.projet.chatmanagement.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatPreview {
    private Long chatId;
    private String name;
    private Object otherMember;
    private String lastMessage;
    private Timestamp lastMessageTimestamp;



    public ChatPreview(Long chatId, String name, Object otherMember, String lastMessage, Timestamp lastMessageTimestamp) {
        this.chatId = chatId;
        this.name = name;
        this.otherMember = otherMember;
        this.lastMessage = lastMessage;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
}