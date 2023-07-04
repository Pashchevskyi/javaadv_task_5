package com.example.javaadv_task_5.service.email_sender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailMessageBuilder {

  public SimpleMailMessage getMessage(String toEmail, String userName, EmailPattern emailPattern) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(emailPattern.getSubject());
    message.setText(String.format("Dear %s!\n%s", userName, emailPattern.getBody()));
    return message;
  }
}
