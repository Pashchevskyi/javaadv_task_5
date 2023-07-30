package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Photo;
import com.example.javaadv_task_5.repository.PhotoRepository;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceBean implements PhotoService {

  private final PhotoRepository photoRepository;

  public PhotoServiceBean(PhotoRepository photoRepository) {
    this.photoRepository = photoRepository;
  }

  @Override
  public Photo create(Photo photo) {
    return photoRepository.save(photo);
  }

  @Override
  public List<Photo> readAll() {
    return photoRepository.findAll();
  }

  @Override
  public Photo read(Long id) {
    return photoRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
  }
}
