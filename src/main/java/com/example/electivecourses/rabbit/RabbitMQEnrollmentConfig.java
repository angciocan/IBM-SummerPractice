package com.example.electivecourses.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQEnrollmentConfig {

    @Bean
    public Queue enrollmentQueue() {
        return new Queue("enrollment-queue", false);
    }

    @Bean
    public DirectExchange enrollmentExchange() {
        return new DirectExchange("enrollment-exchange");
    }

    @Bean
    public Binding enrollmentBinding(Queue enrollmentQueue, DirectExchange enrollmentExchange) {
        return BindingBuilder.bind(enrollmentQueue)
                .to(enrollmentExchange)
                .with("enrollment-routing-key");
    }
}
