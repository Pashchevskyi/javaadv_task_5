package com.example.javaadv_task_5.service.email_sender;

public class EmailPattern {
  private final String subject;
  private final String body;
  EmailPattern(String s, String b) {
    this.subject = s;
    this.body = b;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  public static EmailPattern form() {
    return new EmailPattern(
        "Need to update your password",
        "The expiration date of your password is coming up soon.\n"
        + "Please, don`t delay in updating it.\nBest regards,\nYour company");
  }
}
