package com.example.electivecourses.rabbit.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogger.class);

    public void logMessageSend(Long userId, String message, String exchange, String routingKey) {
        logger.info("Sending message to RabbitMQ: User ID: {}, Exchange: {}, Routing Key: {}, Message: {}", userId, exchange, routingKey, message);
    }

    public void logMessageSentSuccess(Long userId, String exchange, String routingKey) {
        logger.info("Message successfully sent for User ID: {} to Exchange: {} with Routing Key: {}", userId, exchange, routingKey);
    }

    public void logMessageReceived(String message) {
        logger.info("Received message from RabbitMQ: Message: {}", message);
    }

    public void logMessageProcessed(String message) {
        logger.info("Message processed successfully: {}", message);
    }

}
