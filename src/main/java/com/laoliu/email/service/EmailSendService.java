package com.laoliu.email.service;

public interface EmailSendService {
    void sendEmail(String to, String subject, String content);
}
