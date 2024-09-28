package com.example.electivecourses.rabbit;


import com.example.electivecourses.model.entity.Enrollment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEnrollmentConsumer {
    @RabbitListener(queues = "enrollment-queue")
    public void receiveEnrollment(String message) {
        System.out.println("Received enrollment message" + message);
    }

}
