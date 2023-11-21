package com.example.task.service.abstracts;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;
}
