package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.WorkPlace;

public interface WorkPlaceService {
  WorkPlace create(WorkPlace workPlace);
  WorkPlace getById(Integer id);
}
