package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.repository.WorkPlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkPlaceServiceBean implements WorkPlaceService {

  private final WorkPlaceRepository workPlaceRepository;

  public WorkPlaceServiceBean(WorkPlaceRepository workPlaceRepository) {
    this.workPlaceRepository = workPlaceRepository;
  }


  @Override
  public WorkPlace create(WorkPlace workPlace) {
    return workPlaceRepository.save(workPlace);
  }

  @Override
  public WorkPlace getById(Integer id) {
    return workPlaceRepository.findById(id).orElseThrow();
  }
}
