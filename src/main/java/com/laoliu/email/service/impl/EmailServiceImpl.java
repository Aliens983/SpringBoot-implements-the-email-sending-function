package com.laoliu.email.service.impl;

import com.laoliu.email.service.EmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailSendService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;
    
    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 获取配置的发件人邮箱
            String fromEmail = environment.getProperty("spring.mail.username");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
            logger.info("邮件发送成功，发件人：{}，收件人：{}，主题：{}，内容：{}", fromEmail, to, subject, content);
        } catch (Exception e) {
            logger.error("邮件发送失败，收件人：{}，主题：{}，错误信息：{}", to, subject, e.getMessage(), e);
            throw new RuntimeException("邮件发送失败：" + e.getMessage(), e);
        }
    }
}
