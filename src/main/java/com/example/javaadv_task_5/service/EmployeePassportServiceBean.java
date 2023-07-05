package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.EmployeePassport;
import com.example.javaadv_task_5.repository.EmployeePassportRepository;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeePassportServiceBean implements EmployeePassportService {
  private final EmployeePassportRepository employeePassportRepository;

  public EmployeePassportServiceBean(EmployeePassportRepository employeePassportRepository) {
    this.employeePassportRepository = employeePassportRepository;
  }

  @Override
  public EmployeePassport create(EmployeePassport employeePassport) {
    return employeePassportRepository.save(employeePassport);
  }

  @Override
  public List<EmployeePassport> readAll() {
    return employeePassportRepository.findAll();
  }

  @Override
  public EmployeePassport read(Long id) {
    return employeePassportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public List<EmployeePassport> delete(Long id) {
    EmployeePassport employeePassport = employeePassportRepository.findById(id)
        .orElseThrow(ResourceNotFoundException::new);
    employeePassport.deprive();
    employeePassportRepository.save(employeePassport);
    return employeePassportRepository.findAll();
  }
}
