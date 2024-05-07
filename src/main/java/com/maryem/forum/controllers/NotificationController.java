package com.maryem.forum.controllers;

import com.maryem.forum.entities.Notification;
import com.maryem.forum.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/notification")
    public ResponseEntity<List<Notification>> getAllNotifications(){
        List<Notification> list = notificationService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/notification/recent")
    public ResponseEntity<List<Notification>> getAllNotificationsByDate(){
        List<Notification> list = notificationService.findNotificationByDate();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable("id") Long id){
        Optional<Notification> optionalNotification = notificationService.findById(id);
        return optionalNotification.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @PostMapping("/notification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        Notification result = notificationService.create(notification);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/notification")
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification){
        Notification result = notificationService.update(notification);
        return result!=null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/notification/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") Long id){
        Optional<Notification> optionalNotification = notificationService.findById(id);
        if(optionalNotification.isPresent()){
            notificationService.delete(optionalNotification.get());
            return ResponseEntity.ok("Notification deleted successfully.");
        }else {
            return ResponseEntity.noContent().build();
        }
    }
}
