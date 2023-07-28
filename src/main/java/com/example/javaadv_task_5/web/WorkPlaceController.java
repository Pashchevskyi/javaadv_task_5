package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.dto.WorkPlaceDto;
import com.example.javaadv_task_5.dto.WorkPlaceReadDto;
import com.example.javaadv_task_5.service.WorkPlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "WorkPlace", description = "WorkPlace API")
public class WorkPlaceController {
  private final WorkPlaceService workPlaceService;
  private final ObjectMapper mapper;

  public WorkPlaceController(WorkPlaceService workPlaceService, ObjectMapper mapper) {
    this.workPlaceService = workPlaceService;
    this.mapper = mapper;
  }


  @PostMapping("/workplaces")
  @ResponseStatus(HttpStatus.CREATED)
  public WorkPlaceDto create(@RequestBody WorkPlaceDto workPlaceDto) {
    WorkPlace workPlace = mapper.convertValue(workPlaceDto, WorkPlace.class); //mapper.fromWorkPlaceDto(workPlaceDto);
    return mapper.convertValue(workPlaceService.create(workPlace), WorkPlaceDto.class);
    //mapper.toWorkPlaceDto(workPlaceService.create(workPlace));
  }

  @GetMapping("/workplaces/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WorkPlaceReadDto getById(@PathVariable Long id) {
    return mapper.convertValue(workPlaceService.getById(id), WorkPlaceReadDto.class);
    //mapper.toWorkPlaceReadDto(workPlaceService.getById(id));
  }
}
