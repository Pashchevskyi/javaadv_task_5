package com.example.javaadv_task_5.domain.key;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeWorkPlaceKey implements Serializable {
  @Column(name = "employees_id")
  private Long employeeId;

  @Column(name = "work_places_id")
  private Long workPlaceId;

  public EmployeeWorkPlaceKey() {
  }

  public EmployeeWorkPlaceKey(Long employeeId, Long workPlaceId) {
    this.employeeId = employeeId;
    this.workPlaceId = workPlaceId;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Long getWorkPlaceId() {
    return workPlaceId;
  }

  public void setWorkPlaceId(Long workPlaceId) {
    this.workPlaceId = workPlaceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeWorkPlaceKey that = (EmployeeWorkPlaceKey) o;
    return Objects.equals(employeeId, that.employeeId) && Objects.equals(
        workPlaceId, that.workPlaceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeId, workPlaceId);
  }
}
