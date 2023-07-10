package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.EmployeePassport;
import com.example.javaadv_task_5.domain.Photo;
import com.example.javaadv_task_5.dto.EmployeePassportDto;
import com.example.javaadv_task_5.dto.EmployeePassportReadDto;
import com.example.javaadv_task_5.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeePassportMapper {
  EmployeePassportMapper INSTANCE = Mappers.getMapper(EmployeePassportMapper.class);

  EmployeePassportDto employeePassportToEmployeePassportDto(EmployeePassport employeePassport);
  EmployeePassport employeePassportDtoToEmployeePassport(EmployeePassportDto employeePassportDto);
  EmployeePassportReadDto employeePassportToEmployeePassportReadDto(
      EmployeePassport employeePassport);
  Photo map(PhotoDto value);
}
