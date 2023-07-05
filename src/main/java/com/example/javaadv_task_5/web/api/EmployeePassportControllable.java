package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.EmployeePassportDto;
import com.example.javaadv_task_5.dto.EmployeePassportReadDto;
import java.util.List;

public interface EmployeePassportControllable {
  EmployeePassportDto create(EmployeePassportDto employeePassportDto);
  List<EmployeePassportReadDto> readAll();
  EmployeePassportReadDto read(Long id);
  List<EmployeePassportReadDto> delete(Long id);
}
