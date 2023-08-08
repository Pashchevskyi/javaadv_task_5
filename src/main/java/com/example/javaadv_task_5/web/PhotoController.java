package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.Photo;
import com.example.javaadv_task_5.dto.PhotoDto;
import com.example.javaadv_task_5.service.PhotoService;
import com.example.javaadv_task_5.web.api.PhotoControllable;
import com.example.javaadv_task_5.web.api.PhotoDocumentable;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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
public class PhotoController implements PhotoControllable, PhotoDocumentable {

  private final PhotoService photoService;
  private final ObjectMapper mapper;

  public PhotoController(PhotoService photoService, ObjectMapper mapper) {
    this.photoService = photoService;
    this.mapper = mapper;
  }

  @Override
  @PostMapping("/photos")
  @ResponseStatus(HttpStatus.CREATED)
  public PhotoDto create(@RequestBody PhotoDto photoDto) {
    return mapper.convertValue(photoService.create(mapper.convertValue(photoDto, Photo.class)),
        PhotoDto.class);
  }

  @Override
  @GetMapping("/photos")
  @ResponseStatus(HttpStatus.OK)
  public List<PhotoDto> readAll() {
    List<PhotoDto> list = new ArrayList<>();
    photoService.readAll().forEach(p -> list.add(mapper.convertValue(p, PhotoDto.class)));
    return list;
  }

  @Override
  @GetMapping("/photos/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PhotoDto read(@PathVariable Long id) {
    return mapper.convertValue(photoService.read(id), PhotoDto.class);
  }
}
