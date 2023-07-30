package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.EmployeeWorkPlace;
import com.example.javaadv_task_5.domain.WorkPlace;

public interface EmployeeWorkPlaceService {
  EmployeeWorkPlace create(EmployeeWorkPlace employeeWorkPlace);
  Employee activate(Employee employee, WorkPlace workPlace);
  Employee deactivate(Employee employee);
}
