package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.Address;
import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.dto.AddressDto;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeOnlyDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeToEmployeeDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);
    EmployeeOnlyDto employeeToEmployeeOnlyDto(Employee employee);
    Address map(AddressDto value);
}
