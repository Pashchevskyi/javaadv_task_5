package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.EmployeeWorkPlace;
import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import com.example.javaadv_task_5.repository.EmployeeWorkPlaceRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EmployeeWorkPlaceServiceBean implements EmployeeWorkPlaceService {

  private final EmployeeWorkPlaceRepository employeeWorkPlaceRepository;
  private final EmployeeRepository employeeRepository;

  public EmployeeWorkPlaceServiceBean(EmployeeRepository employeeRepository,
      EmployeeWorkPlaceRepository employeeWorkPlaceRepository) {
    this.employeeRepository = employeeRepository;
    this.employeeWorkPlaceRepository = employeeWorkPlaceRepository;
  }


  @Override
  public EmployeeWorkPlace create(EmployeeWorkPlace employeeWorkPlace) {
    return employeeWorkPlaceRepository.save(employeeWorkPlace);
  }

  @Override
  public void createByStoredProcedure(Long employeeId, Long workPlaceId) {
    employeeWorkPlaceRepository.create(employeeId, workPlaceId);
  }

  @Override
  public void deleteByStoredProcedure(Long employeeId, Long workPlaceId) {
    employeeWorkPlaceRepository.delete(employeeId, workPlaceId);
  }


  @Override
  public Employee activate(Employee employee, WorkPlace workPlace) {
    List<EmployeeWorkPlace> employeeWorkPlaces = employeeWorkPlaceRepository.findAll();
    for (EmployeeWorkPlace ewp : employeeWorkPlaces) {
      if (ewp.getEmployee().getId() == employee.getId()) {
        ewp.setActive(ewp.getWorkPlace().getId() == workPlace.getId());
      }
    }

    return employeeRepository.save(employee);
  }

  @Override
  public Employee deactivate(Employee employee) {
    Set<EmployeeWorkPlace> employeeWorkPlaces = employee.getWorkPlaces();
    for (EmployeeWorkPlace ewp : employeeWorkPlaces) {
      if (ewp.getActive() == Boolean.TRUE) {
        ewp.setActive(false);
      }
    }

    return employeeRepository.save(employee);
  }
}
