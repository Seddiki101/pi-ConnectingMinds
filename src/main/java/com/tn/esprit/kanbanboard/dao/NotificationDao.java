package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDao extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationByMemberId(Long memberId);
}
