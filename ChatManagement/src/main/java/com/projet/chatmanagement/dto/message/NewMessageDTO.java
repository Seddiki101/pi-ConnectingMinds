package com.projet.chatmanagement.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewMessageDTO {

    private Long senderId;
    private Long recieverId;
    private String content;
}
