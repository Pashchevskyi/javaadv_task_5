package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.dto.EmployeeDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class EmployeeMapper extends CustomMapper<Employee, EmployeeDto> {

    @Override
    public void mapBtoA(EmployeeDto dto, Employee entity, MappingContext context) {
        super.mapBtoA(dto, entity, context);
    }
}
