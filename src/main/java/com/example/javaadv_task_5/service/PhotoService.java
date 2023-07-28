package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Photo;
import java.util.List;

public interface PhotoService {
  Photo create(Photo photo);
  List<Photo> readAll();
  Photo read(Long id);
}
