package com.projet.chatmanagement.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatCreateDTO {
    private String name;
    private List<Long> memberIds;

}
