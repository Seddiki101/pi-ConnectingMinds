package com.maryem.forum.servicesImpl;

import com.maryem.forum.daos.NotificationDao;
import com.maryem.forum.entities.Notification;
import com.maryem.forum.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
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
    public List<Notification> findNotificationByUserId(Long ID) {
        return notificationDao.findNotificationByMemberId(ID);
    }

    @Override
    public List<Notification> findNotificationByProjectId(Long ID) {
        return notificationDao.findNotificationByProjectId(ID);
    }

    @Override
    public List<Notification> findNotificationByDate() {
        return notificationDao.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Notification create(Notification notification) {
            Notification result = notificationDao.save(notification);
            return result;
    }

    @Override
    public Notification update(Notification notification) {
        Optional<Notification> optionalNotification = notificationDao.findById(notification.getNotificationId());
        if(optionalNotification.isPresent()){
            Notification updatedNotification = optionalNotification.get();
            updatedNotification.setIsOpened(notification.getIsOpened());
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
