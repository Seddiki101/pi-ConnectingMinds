package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void SendMessage(final Notification notification){
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

}
