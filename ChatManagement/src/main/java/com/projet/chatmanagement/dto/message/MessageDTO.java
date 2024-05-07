package com.projet.chatmanagement.dto.message;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Long messageId;
    private String content;
    private Long chatId;
    private Long userId;
    private LocalDateTime timestamp;
    private boolean seen;
    private boolean deleted;
}
