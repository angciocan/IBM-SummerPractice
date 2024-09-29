package com.example.electivecourses.rabbit.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQNotificationConsumer {
    @RabbitListener(queues = "notification-queue")
    public void receiveNotification(String message) {
        System.out.println("Received message from RabbitMQNotificationConsumer: " + message);
    }
}
