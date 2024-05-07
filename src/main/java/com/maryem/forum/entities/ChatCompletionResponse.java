package com.maryem.forum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionResponse implements Serializable {
  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
   // private int id;
    private List<Choice> choices;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private ChatMessage message;
        private int max_tokens=50;
    }
}
