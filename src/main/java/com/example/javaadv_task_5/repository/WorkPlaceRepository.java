package com.example.javaadv_task_5.repository;

import com.example.javaadv_task_5.domain.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Integer> {

}
