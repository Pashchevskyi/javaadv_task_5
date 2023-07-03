package com.example.javaadv_task_5.repository;

import com.example.javaadv_task_5.domain.EmployeePassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePassportRepository extends JpaRepository<EmployeePassport, Long> {

}
