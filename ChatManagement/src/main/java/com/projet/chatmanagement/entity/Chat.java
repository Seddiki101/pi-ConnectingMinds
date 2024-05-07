package com.projet.chatmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "chat_users", joinColumns = @JoinColumn(name = "chat_id"))
    @Column(name = "user_id")

    private Set<Long> memberIds;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    //used @JsonManagedReference to avoid infinite json recursion
    @JsonManagedReference
    private List<Message> messages;

    //Transient means it's not stored in the database
//    @Transient
//    private Message lastMessage;


    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }
}
