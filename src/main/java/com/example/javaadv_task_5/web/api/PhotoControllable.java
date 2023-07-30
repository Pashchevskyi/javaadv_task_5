package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.PhotoDto;
import java.util.List;

public interface PhotoControllable {
  PhotoDto create(PhotoDto photoDto);
  List<PhotoDto> readAll();
  PhotoDto read(Long id);
}
