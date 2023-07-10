package com.example.javaadv_task_5.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passports")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePassport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String uuid = UUID.randomUUID().toString();
  private String series;
  private String number;
  private String bodyHanded;
  private Date handDate;
  private LocalDateTime expireDate;
  @Column(name = "is_handed")
  private Boolean isHanded = Boolean.FALSE;
  @OneToOne
  @JoinColumn(name = "photo_id")
  private Photo photo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSeries() {
    return series;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getBodyHanded() {
    return bodyHanded;
  }

  public void setBodyHanded(String bodyHanded) {
    this.bodyHanded = bodyHanded;
  }

  public Date getHandDate() {
    return handDate;
  }

  public void setHandDate(Date handDate) {
    this.handDate = handDate;
  }

  public Boolean isHanded() {
    return this.isHanded;
  }

  public void hand() {
    isHanded = true;
  }
  public void deprive() {isHanded = false;}

  public Photo getPhoto() {
    return photo;
  }

  public void setPhoto(Photo photo) {
    this.photo = photo;
  }
}
