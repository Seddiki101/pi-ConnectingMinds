package com.projet.chatmanagement.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageCreateDTO {
    private String content;
    private Long chatId;
    private Long userId;
    private LocalDateTime timestamp;
}

