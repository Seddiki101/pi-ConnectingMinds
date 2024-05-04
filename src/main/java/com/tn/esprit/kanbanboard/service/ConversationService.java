package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationService {
    List<Conversation> findAll();
    List<Conversation> findAllByUserId(Long userId);
    Optional<Conversation> findById(Long ID);
    Conversation create(Conversation conversation);
    Conversation update(Conversation conversation);
    void delete(Long ID);
}
