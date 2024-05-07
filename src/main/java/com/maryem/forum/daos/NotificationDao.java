package com.maryem.forum.daos;

import com.maryem.forum.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationByMemberId(Long memberId);
    List<Notification> findNotificationByProjectId(Long projectId);
    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    List<Notification> findAllByOrderByCreatedAtDesc();
}
