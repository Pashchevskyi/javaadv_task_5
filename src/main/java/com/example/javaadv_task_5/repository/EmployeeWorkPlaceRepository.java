package com.example.javaadv_task_5.repository;

import com.example.javaadv_task_5.domain.EmployeeWorkPlace;
import com.example.javaadv_task_5.domain.key.EmployeeWorkPlaceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeWorkPlaceRepository extends JpaRepository<EmployeeWorkPlace, EmployeeWorkPlaceKey> {

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "call insert_users_work_places(?1, ?2);", nativeQuery = true)
  void create(Long employeeId, Long workPlaceId);

  @Transactional
  @Modifying
  @Query(value = "call delete_users_work_places(?1, ?2);", nativeQuery = true)
  void delete(Long employeeId, Long workPlaceId);
}
