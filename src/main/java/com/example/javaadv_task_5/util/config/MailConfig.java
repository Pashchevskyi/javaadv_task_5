package com.example.javaadv_task_5.util.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    Properties javaMailProps = mailSender.getJavaMailProperties();

    try(InputStream is = new FileInputStream("./src/main/resources/application.properties")) {

      Properties props = new Properties();
      props.load(is);

      javaMailProps.put("mail.protocol", props.getProperty("mail.protocol"));
      javaMailProps.put("mail.test-connection", props.getProperty("mail.test-connection"));
      javaMailProps.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
      javaMailProps.put("mail.smtp.starttls.enable",
          props.getProperty("mail.smtp.starttls.enable"));

      mailSender.setHost(props.getProperty("smtp.host"));
      mailSender.setPort(Integer.parseInt(props.getProperty("smtp.port")));
      mailSender.setUsername(props.getProperty("smtp.username"));
      mailSender.setPassword(props.getProperty("smtp.password"));
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    return mailSender;
  }

}
