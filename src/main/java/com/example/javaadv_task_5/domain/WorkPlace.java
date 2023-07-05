package com.example.javaadv_task_5.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workplaces")
@Builder
public class WorkPlace {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Boolean airCondition = Boolean.TRUE;
  private Boolean coffeeMachine = Boolean.TRUE;
  @ManyToMany(mappedBy = "workPlaces")
  private Set<Employee> employees = new HashSet<>();

  public WorkPlace(Integer id, String name, Boolean airCondition, Boolean coffeeMachine) {
    this.id = id;
    this.name = name;
    this.airCondition = airCondition;
    this.coffeeMachine = coffeeMachine;
  }

  public WorkPlace() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
