package com.example.javaadv_task_5.service.email_sender;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceBean implements EmailSenderService {

  private final JavaMailSender mailSender;
  private final SimpleMailMessageBuilder messageBuilder;

  public EmailSenderServiceBean(JavaMailSender mailSender, SimpleMailMessageBuilder messageBuilder) {
    this.mailSender = mailSender;
    this.messageBuilder = messageBuilder;
  }

  @Override
  public void send(String toEmail, String userName, EmailPattern pattern) throws MailException {
    SimpleMailMessage message = messageBuilder.getMessage(toEmail, userName, pattern);
    mailSender.send(message);
  }
}
