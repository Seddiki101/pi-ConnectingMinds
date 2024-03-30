package com.tn.esprit.kanbanboard.serviceImpl;

import com.tn.esprit.kanbanboard.dao.GroupDao;
import com.tn.esprit.kanbanboard.dao.NotificationDao;
import com.tn.esprit.kanbanboard.entity.Group;
import com.tn.esprit.kanbanboard.entity.Notification;
import com.tn.esprit.kanbanboard.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;
    private final GroupDao groupDao;
    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao,GroupDao groupDao) {
        this.notificationDao = notificationDao;
        this.groupDao = groupDao;
    }

    @Override
    public List<Notification> findAll() {
        return notificationDao.findAll();
    }

    @Override
    public Optional<Notification> findById(Long ID) {
        return notificationDao.findById(ID);
    }

    @Override
    public Notification create(Long groupId,Notification notification) {
        Optional<Group> optionalGroup = groupDao.findById(groupId);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            notification.setGroup(group);
            Notification result = notificationDao.save(notification);
            return result;
        }
        System.out.println("Error : Group doesn't exist.");
        return null;
    }

    @Override
    public Notification update(Notification notification) {
        Optional<Notification> optionalNotification = notificationDao.findById(notification.getNotificationId());
        if(optionalNotification.isPresent()){
            Notification updatedNotification = optionalNotification.get();
            updatedNotification.setContent(notification.getContent());
            updatedNotification.setIsOpened(notification.getIsOpened());
            updatedNotification.setCreatedAt(notification.getCreatedAt());
            updatedNotification.setMemberId(notification.getMemberId());
            Notification result = notificationDao.save(updatedNotification);
            return result;
            }
        System.out.println("Error : Notification doesn't exist.");
        return null;
    }

    @Override
    public void delete(Notification notification) {
        notificationDao.delete(notification);
    }
}
