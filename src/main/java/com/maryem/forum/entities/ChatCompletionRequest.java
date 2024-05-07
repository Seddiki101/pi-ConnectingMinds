package com.maryem.forum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data

//@Entity
public class ChatCompletionRequest implements Serializable {
  // @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   //private int id;
    private String model;
    private List<ChatMessage> messages;
    private int max_tokens=50;

    public ChatCompletionRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<ChatMessage>();
        this.messages.add(new ChatMessage("user",prompt));
    }
}
