package com.projet.chatmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String content;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(name="user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    //to prevent json infinte recursion
    @JsonBackReference
    private Chat chat;

    private Boolean seen= false;
    private Boolean deleted= false;
}
