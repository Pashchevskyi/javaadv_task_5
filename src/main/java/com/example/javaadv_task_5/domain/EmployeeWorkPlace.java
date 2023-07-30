package com.example.javaadv_task_5.domain;

import com.example.javaadv_task_5.domain.key.EmployeeWorkPlaceKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_work_places")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWorkPlace {
  @EmbeddedId
  private EmployeeWorkPlaceKey id;

  @ManyToOne
  @MapsId("employeeId")
  @JoinColumn(name = "employees_id")
  @JsonBackReference
  private Employee employee;

  @ManyToOne
  @MapsId("workPlaceId")
  @JoinColumn(name = "work_places_id")
  private WorkPlace workPlace;

  private Boolean active;

  public EmployeeWorkPlaceKey getId() {
    return id;
  }

  public void setId(EmployeeWorkPlaceKey id) {
    this.id = id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public WorkPlace getWorkPlace() {
    return workPlace;
  }

  public void setWorkPlace(WorkPlace workPlace) {
    this.workPlace = workPlace;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
