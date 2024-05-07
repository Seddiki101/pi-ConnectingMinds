package com.projet.chatmanagement.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageUpdateDTO {
    private Long messageId;
    private String content;
}
