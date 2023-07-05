package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.Address;
import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.dto.AddressDto;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import com.example.javaadv_task_5.dto.WorkPlaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeToEmployeeDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);
    Employee employeeReadDtoToEmployee(EmployeeReadDto employeeReadDto);
    Address map(AddressDto value);
    WorkPlace mapWorkPlace(WorkPlaceDto value);
}
