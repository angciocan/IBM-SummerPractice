package com.example.ElectivCourses.rabbit.general;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue generalQueue(){
        return new Queue("queue-names", false);
    }


    @Bean
    public Exchange generalExchange(){

        return new DirectExchange("exchange-name");
    }

    @Bean
    public Binding generalBinding(Queue generalQueue, Exchange generalExchange){
        return BindingBuilder.bind(generalQueue)
                .to(generalExchange)
                .with("routing-key")
                .noargs();
    }



}
