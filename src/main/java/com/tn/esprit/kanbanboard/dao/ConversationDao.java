package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationDao extends JpaRepository<Conversation,Long> {
    List<Conversation> findByUserIdOrderByTimestampDesc(Long userId);
}
