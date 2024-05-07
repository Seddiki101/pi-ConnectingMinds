package com.maryem.forum.servicesImpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryem.forum.entities.Notification;
import com.maryem.forum.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class ConsumerServiceImpl {
    private final NotificationService notificationService;

    private final WebSocketService webSocketService;
@Autowired
    public ConsumerServiceImpl(NotificationService notificationService,WebSocketService webSocketService) {
        this.notificationService = notificationService;
        this.webSocketService = webSocketService;
    }

    @KafkaListener(topics = "kanban-topic")
    public void receiveNotification(String message) {
        Notification savedOne= new Notification();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Notification notification = objectMapper.readValue(message, Notification.class);
            if(notification!=null){
                    savedOne = notificationService.create(notification);
                    webSocketService.SendMessage(savedOne);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
