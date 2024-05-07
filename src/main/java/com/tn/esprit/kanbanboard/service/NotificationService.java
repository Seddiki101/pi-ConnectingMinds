package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Notification;
import org.aspectj.weaver.ast.Not;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> findAll();
    Optional<Notification> findById(Long ID);
    Notification create(Notification notification);
    Notification update(Notification notification);
    void delete(Notification notification);
}
