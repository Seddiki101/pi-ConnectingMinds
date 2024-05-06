package com.projet.chatmanagement.dao;

import com.projet.chatmanagement.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.chat.chatId = :chatId")
    List<Message> findByChatId(Long chatId);
}
