package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ProducerController {
    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;
    private static final String TOPIC = "kanban-topic";
    @PostMapping("/send-notification")
    public void sendMessage(@RequestBody Notification notification){
        LocalDateTime datetime = LocalDateTime.now();
        notification.setCreatedAt(datetime);
        kafkaTemplate.send(TOPIC,notification);
    }
}
