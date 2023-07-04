package com.example.javaadv_task_5.service.email_sender;

import org.springframework.mail.MailException;

public interface EmailSenderService {
  void send(String toEmail, String userName, EmailPattern emailPattern) throws MailException;
}
