package com.projet.chatmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Enable WebSocket message handling, backed by a message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Defines the message broker which will handle messages from `/topic`
        config.enableSimpleBroker("/topic");
        // Defines the prefix for the endpoints that the clients will send messages to
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the `/ws-chat` endpoint, enabling SockJS fallback options so that alternative
        // messaging options may be used if WebSocket is not available
        registry.addEndpoint("/ws-chat").setAllowedOrigins("http://localhost:4200","http://localhost:4200/","http://localhost:4200/chat").withSockJS();
    }
}