package com.projet.chatmanagement.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);
    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String topicSuffix, Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            messagingTemplate.convertAndSend("/topic/" + topicSuffix, jsonMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting message to JSON: " + e.getMessage());
        }
    }

    public void notifyFrontend(String entityTopic) {
        if (entityTopic == null) {
            LOG.error("Failed to get entity topic");
            return;
        }
        Map<String, String> message = new HashMap<>();
        message.put("message", "Update from server");
        System.out.println("/topic/" + entityTopic);  // Ensure this prints the expected topic name
        this.sendMessage(entityTopic, message);  // Corrected without 'topic:' prefix
    }
}
