package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.EmployeePassport;
import com.example.javaadv_task_5.dto.EmployeePassportDto;
import com.example.javaadv_task_5.dto.EmployeePassportReadDto;
import com.example.javaadv_task_5.service.EmployeePassportService;
import com.example.javaadv_task_5.web.api.EmployeePassportControllable;
import com.example.javaadv_task_5.web.api.EmployeePassportDocumentable;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeePassportController implements EmployeePassportControllable,
    EmployeePassportDocumentable {
  private final EmployeePassportService employeePassportService;
  private final ObjectMapper mapper;

  public EmployeePassportController(EmployeePassportService employeePassportService, ObjectMapper mapper) {
    this.employeePassportService = employeePassportService;
    this.mapper = mapper;
  }

  @Override
  @PostMapping("/passports")
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeePassportDto create(@RequestBody EmployeePassportDto employeePassportDto) {
    return mapper.convertValue(employeePassportService.create(
        mapper.convertValue(employeePassportDto, EmployeePassport.class)),
        EmployeePassportDto.class);
  }

  @Override
  @GetMapping("/passports")
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeePassportReadDto> readAll() {
    List<EmployeePassportReadDto> list = new ArrayList<>();
    employeePassportService.readAll()
        .forEach(p -> list.add(mapper.convertValue(p, EmployeePassportReadDto.class)));
    return list;
  }

  @Override
  @GetMapping("/passports/{id}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeePassportReadDto read(@PathVariable Long id) {
    return mapper.convertValue(employeePassportService.read(id), EmployeePassportReadDto.class);
  }

  @Override
  @PatchMapping("/passports/{id}")
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeePassportReadDto> delete(@PathVariable Long id) {
    List<EmployeePassportReadDto> list = new ArrayList<>();
    employeePassportService.delete(id)
        .forEach(p -> list.add(mapper.convertValue(p, EmployeePassportReadDto.class)));
    return list;
  }

  @Override
  @PatchMapping("/passports/{passportId}/photos/{photoId}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeePassportReadDto updatePhoto(@PathVariable Long passportId, @PathVariable Long photoId) {
    return mapper.convertValue(employeePassportService.updatePhoto(passportId, photoId),
        EmployeePassportReadDto.class);
  }

  @Override
  @PatchMapping("/passports/detach-photo/{passportId}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeePassportReadDto detachPhoto(@PathVariable Long passportId) {
    return mapper.convertValue(employeePassportService.detachPhoto(passportId),
        EmployeePassportReadDto.class);
  }
}
