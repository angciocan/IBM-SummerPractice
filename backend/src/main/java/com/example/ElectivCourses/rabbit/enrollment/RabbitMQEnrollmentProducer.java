package com.example.ElectivCourses.rabbit.enrollment;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEnrollmentProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEnrollment(String message) {
        rabbitTemplate.convertAndSend("enrollment-exchange","enrollment-routing-key",message);
    }
}
