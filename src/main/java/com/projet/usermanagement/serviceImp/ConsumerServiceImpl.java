package com.projet.usermanagement.serviceImp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.usermanagement.dao.UserRepository;
import com.projet.usermanagement.entity.Notification;
import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class ConsumerServiceImpl {
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final WebSocketService webSocketService;
@Autowired
    public ConsumerServiceImpl(NotificationService notificationService,UserRepository userRepository,WebSocketService webSocketService) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.webSocketService = webSocketService;
    }

    @KafkaListener(topics = "kanban-topic")
    public void receiveNotification(String message) {
        Notification savedOne= new Notification();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Notification notification = objectMapper.readValue(message, Notification.class);
            if(notification!=null){
                Optional<User> optionalUser = userRepository.findUserById(notification.getMemberId());
                if(optionalUser.isPresent()){
                    User user = optionalUser.get();
                    notification.setContent(notification.getContent()+user.getFirstName()+" "+user.getLastName()+".");
                    savedOne = notificationService.create(notification);
                    webSocketService.SendMessage(savedOne);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
