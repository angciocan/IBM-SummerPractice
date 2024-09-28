package com.example.electivecourses.rabbit;

import com.example.electivecourses.rabbit.general.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public RabbitMQController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        rabbitMQProducer.sendMessage(message);
        return "Message sent" + message;
    }

    @PostMapping("/sendEnrollment")
    public String sendEnrollmentMessage(@RequestParam("message") String message) {
        rabbitMQProducer.sendMessage(message);
        return "Message sent" + message;
    }
}
