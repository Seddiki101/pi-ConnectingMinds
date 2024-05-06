package com.projet.usermanagement.dao;

import com.projet.usermanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationByMemberId(Long memberId);
    List<Notification> findNotificationByProjectId(Long projectId);
}
