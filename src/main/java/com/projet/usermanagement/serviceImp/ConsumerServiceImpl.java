package com.projet.usermanagement.serviceImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.usermanagement.entity.Notification;
import com.projet.usermanagement.service.ConsumerService;
import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerServiceImpl implements ConsumerService {
    @KafkaListener(topics = "kanban-topic")
    @Override
    public void consumeMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Notification deserializedMessage;
        try {
            deserializedMessage = objectMapper.readValue(message, Notification.class);
            System.out.println("Consumed message: " + deserializedMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
