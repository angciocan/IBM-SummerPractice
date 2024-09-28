package com.example.electivecourses.rabbit.general;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    @RabbitListener(queues = "queue-name")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
