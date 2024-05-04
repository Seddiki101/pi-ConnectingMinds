package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.ConversationDao;
import com.tn.esprit.kanbanboard.entity.Conversation;
import com.tn.esprit.kanbanboard.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationDao conversationDao;
    @Autowired
    public ConversationServiceImpl(ConversationDao conversationDao) {
        this.conversationDao = conversationDao;
    }

    @Override
    public List<Conversation> findAll() {
        return conversationDao.findAll();
    }

    @Override
    public List<Conversation> findAllByUserId(Long userId) {
        return conversationDao.findByUserId(userId);
    }

    @Override
    public Optional<Conversation> findById(Long ID) {
        return conversationDao.findById(ID);
    }

    @Override
    public Conversation create(Conversation conversation) {
        return conversationDao.save(conversation);
    }

    @Override
    public Conversation update(Conversation conversation) {
        return conversationDao.save(conversation);
    }

    @Override
    public void delete(Long ID) {
        conversationDao.deleteById(ID);
    }
}
