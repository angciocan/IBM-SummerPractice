package com.example.electivecourses.rabbit.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQNotificationConsumer {
    @RabbitListener(queues = "notification-queue", concurrency = "10")
    public void receiveEnrollment(String message) {
        System.out.println("Received notification message " + message);
    }
}
