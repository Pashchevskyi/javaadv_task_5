package com.example.javaadv_task_5.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "workplaces")
@AllArgsConstructor
@Builder
public class WorkPlace {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Boolean airCondition = Boolean.TRUE;
  private Boolean coffeeMachine = Boolean.TRUE;
  @OneToMany(mappedBy = "workPlace")
  private Set<EmployeeWorkPlace> employees = new HashSet<>();

  public WorkPlace() {
  }

  public WorkPlace(Long id, String name, Boolean airCondition, Boolean coffeeMachine) {
    this.id = id;
    this.name = name;
    this.airCondition = airCondition;
    this.coffeeMachine = coffeeMachine;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getAirCondition() {
    return airCondition;
  }

  public void setAirCondition(Boolean airCondition) {
    this.airCondition = airCondition;
  }

  public Boolean getCoffeeMachine() {
    return coffeeMachine;
  }

  public void setCoffeeMachine(Boolean coffeeMachine) {
    this.coffeeMachine = coffeeMachine;
  }
}
