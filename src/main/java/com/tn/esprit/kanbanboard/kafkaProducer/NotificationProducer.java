package com.tn.esprit.kanbanboard.kafkaProducer;

import com.tn.esprit.kanbanboard.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {
    private static final String TOPIC = "kanban-topic";

    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        kafkaTemplate.send(TOPIC, notification);
    }
}
