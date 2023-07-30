package com.example.javaadv_task_5.repository;

import com.example.javaadv_task_5.domain.EmployeeWorkPlace;
import com.example.javaadv_task_5.domain.key.EmployeeWorkPlaceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeWorkPlaceRepository extends JpaRepository<EmployeeWorkPlace, EmployeeWorkPlaceKey> {
  @Query(value = "select * from users_work_places where employees_id = ?1 and work_places_id = ?2",
      nativeQuery = true)
  EmployeeWorkPlace findByEmployeeIdAndWorkPlaceId(Long employeeId, Long workPlaceId);
}
