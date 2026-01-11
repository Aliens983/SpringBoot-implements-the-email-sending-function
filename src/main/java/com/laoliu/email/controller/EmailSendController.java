package com.laoliu.email.controller;

import com.laoliu.email.dto.EmailRequestDto;
import com.laoliu.email.service.EmailSendService;
import com.laoliu.email.vo.EmailResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailSendController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailSendController.class);
    
    @Autowired
    private EmailSendService emailSendService;
    
    @PostMapping
    public ResponseEntity<EmailResponseVO> sendEmail(@RequestBody EmailRequestDto request) {
        try {
            // 参数验证
            if (request == null) {
                return ResponseEntity.badRequest().body(new EmailResponseVO("请求参数不能为空", false));
            }
            String to = request.getTo();
            String subject = request.getSubject();
            String content = request.getContent();
            
            if (to == null || to.isEmpty()) {
                return ResponseEntity.badRequest().body(new EmailResponseVO("收件人邮箱不能为空", false));
            }
            if (subject == null || subject.isEmpty()) {
                return ResponseEntity.badRequest().body(new EmailResponseVO("邮件主题不能为空", false));
            }
            if (content == null || content.isEmpty()) {
                return ResponseEntity.badRequest().body(new EmailResponseVO("邮件内容不能为空", false));
            }
            
            // 发送邮件
            emailSendService.sendEmail(to, subject, content);
            logger.info("邮件发送请求处理成功，收件人：{}，主题：{}", to, subject);
            return ResponseEntity.ok(new EmailResponseVO("邮件发送成功"));
        } catch (RuntimeException e) {
            logger.error("邮件发送请求处理失败，错误信息：{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponseVO("邮件发送失败：" + e.getMessage(), false));
        } catch (Exception e) {
            logger.error("邮件发送请求处理异常，错误信息：{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponseVO("邮件发送失败：系统异常", false));
        }
    }

}
