package com.projet.usermanagement.service;

import com.projet.usermanagement.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> findAll();
    Optional<Notification> findById(Long ID);
    List<Notification> findNotificationByUserId(Long ID);
    List<Notification> findNotificationByProjectId(Long ID);
    Notification create(Notification notification);
    Notification update(Notification notification);
    void delete(Notification notification);
}
