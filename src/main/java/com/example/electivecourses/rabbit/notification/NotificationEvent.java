package com.example.electivecourses.rabbit.notification;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationEvent {

    private Long userId;
    private String message;
    private String notificationType;
    private LocalDateTime timestamp;

    public NotificationEvent(Long userId, String message, String notificationType, LocalDateTime timestamp) {
        this.userId = userId;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
    }
    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
