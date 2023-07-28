package com.example.javaadv_task_5.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String href;
  private Date date = new Date();
  private Boolean isAttached = Boolean.FALSE;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHref() {
    return href;
  }
  public void setHref(String href) { this.href = href; }

  public Date getDate() {
    return date;
  }
  public void setDate(Date date) { this.date = (date != null) ? date : new Date(); }

  public Boolean isAttached() {
    return this.isAttached != null && this.isAttached;
  }

  public void attach() {
    this.isAttached = Boolean.TRUE;
  }
  public void detach() { this.isAttached = Boolean.FALSE; }
}
