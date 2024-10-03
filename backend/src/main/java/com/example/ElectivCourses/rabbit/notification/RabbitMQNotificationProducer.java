package com.example.ElectivCourses.rabbit.notification;

import com.example.ElectivCourses.rabbit.audit.AuditLogger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitMQNotificationProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AuditLogger auditLogger;

    public void notifyMessage(Long userId, String message) {
        NotificationEvent notification = new NotificationEvent(userId, message, "New message", LocalDateTime.now());

        auditLogger.logMessageSend(userId, message, "notification-exchange", "notification-routing-key");

        rabbitTemplate.convertAndSend("notification-exchange","notification-routing-key", notification);

        auditLogger.logMessageSentSuccess(userId,"notification-exchange", "notification-routing-key");

    }
}
