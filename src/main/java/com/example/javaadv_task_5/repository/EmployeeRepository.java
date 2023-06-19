package com.example.javaadv_task_5.repository;

import com.example.javaadv_task_5.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM users", nativeQuery = true)

    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = "select * from users join addresses on users.id = addresses.employee_id " +
            "where users.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    Employee findByName(String name);

    Employee findEmployeeByEmailNotNull();

    List<Employee> findByEmailNull();

    @Query(value = "select e from Employee e where e.isDeleted=false and substring(e.country, 1, 1) between 'a' and 'z'")
    List<Employee> findByCountryStartingWithLowercase();

    @Query(value = "select * from users where is_deleted=false", nativeQuery = true)
    List<Employee> findAll();

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

}
