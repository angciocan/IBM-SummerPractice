package com.example.ElectivCourses.rabbit.enrollment;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEnrollmentConsumer {
    @RabbitListener(queues = "enrollment-queue", concurrency = "10")
    public void receiveEnrollment(String message) {
        System.out.println("Received enrollment message " + message);
    }

}
