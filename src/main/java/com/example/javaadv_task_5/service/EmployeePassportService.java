package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.EmployeePassport;
import java.util.List;

public interface EmployeePassportService {
  EmployeePassport create(EmployeePassport employeePassport);
  List<EmployeePassport> readAll();
  EmployeePassport read(Long id);
  List<EmployeePassport> delete(Long id);
  EmployeePassport updatePhoto(Long passportId, Long photoId);
  EmployeePassport detachPhoto(Long passportId);
}
