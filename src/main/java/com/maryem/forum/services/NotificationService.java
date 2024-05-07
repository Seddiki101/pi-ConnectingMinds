package com.maryem.forum.services;

import com.maryem.forum.entities.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> findAll();
    Optional<Notification> findById(Long ID);
    List<Notification> findNotificationByUserId(Long ID);
    List<Notification> findNotificationByProjectId(Long ID);
    List<Notification> findNotificationByDate();
    Notification create(Notification notification);
    Notification update(Notification notification);
    void delete(Notification notification);
}
