package com.example.electivecourses.rabbit.notification;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitMQNotificationProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notifyMessage(Long userId, String message) {
        NotificationEvent notification = new NotificationEvent(userId, message, "New message", LocalDateTime.now());
        rabbitTemplate.convertAndSend("notification-exchange","notification-routing-key", notification);
    }
}
