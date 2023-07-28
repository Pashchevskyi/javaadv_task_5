package com.example.javaadv_task_5;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.javaadv_task_5.domain.Address;
import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.Gender;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Employee Repository Tests")
public class RepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("Save employee test")
    public void saveEmployeeTest() {

        var employee = Employee.builder()
                .name("Mark")
                .country("England")
                .addresses(new HashSet<>(Set.of(
                        Address
                                .builder()
                                .country("UK")
                                .build())))
                .gender(Gender.M)
                .build();

        employeeRepository.save(employee);

        var employeeFromLowerCasedCountry = Employee.builder()
            .name("Vanya")
            .country("russia")
            .addresses(new HashSet<>(Set.of(
                Address.builder()
                    .country("russia")
                    .city("moscow")
                    .street("tverskaya-yamskaya")
                    .addressHasActive(true)
                    .build()
            )))
            .build();

        employeeRepository.save(employeeFromLowerCasedCountry);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
        Assertions.assertThat(employee.getId()).isEqualTo(1);
        Assertions.assertThat(employee.getName()).isEqualTo("Mark");
        Assertions.assertThat(employee.getCountry()).isEqualTo("England");
        Assertions.assertThat(employee.getGender()).isEqualTo(Gender.M);
        Assertions.assertThat(employeeFromLowerCasedCountry.getId()).isEqualTo(2);
        Assertions.assertThat(employeeFromLowerCasedCountry.getName()).isEqualTo("Vanya");
        Assertions.assertThat(employeeFromLowerCasedCountry.getCountry())
            .isEqualTo("russia");
    }

    @Test
    @Order(2)
    @DisplayName("Get employee by id test")
    public void getEmployeeTest() {

        var employee = employeeRepository.findById(1L).orElseThrow();

        Assertions.assertThat(employee.getId()).isEqualTo(1);
        Assertions.assertThat(employee.getName()).isEqualTo("Mark");
        Assertions.assertThat(employee.getCountry()).isEqualTo("England");
        Assertions.assertThat(employee.getGender()).isEqualTo(Gender.M);
    }

    @Test
    @Order(3)
    @DisplayName("Get employees test")
    public void getListOfEmployeeTest() {

        var employeesList = employeeRepository.findAll();

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @DisplayName("Get employees with no E-mail test")
    public void findByEmailNullTest() {
      List<Employee> employees = employeeRepository.findByEmailNull();

      Assertions.assertThat(employees.size()).isGreaterThan(0);
      employees.forEach(e -> Assertions.assertThat(e.getEmail()).isNull());
    }

    @Test
    @Order(5)
    @DisplayName("Get employees with lower cased countries test")
    public void findByCountryStartingWithLowercaseTest() {
        List<Employee> employees = employeeRepository.findByCountryStartingWithLowercase();

        Assertions.assertThat(employees.size()).isGreaterThan(0);
        employees.forEach(e -> Assertions.assertThat(e.getCountry().charAt(0)).isLowerCase());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    @DisplayName("Update employee test")
    public void updateEmployeeTest() {

        var employee = employeeRepository.findById(1L).orElseThrow();

        employee.setName("Martin");
        var employeeUpdated = employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdated.getName()).isEqualTo("Martin");

    }

    @Test
    @Order(7)
    @DisplayName("Find employee by gender test")
    public void findByGenderTest() {

        var employees = employeeRepository.findByGender(Gender.M.toString(), "UK");

        assertThat(employees.get(0).getGender()).isEqualTo(Gender.M);
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    @DisplayName("Delete employee test")
    public void deleteEmployeeTest() {

        var employee = employeeRepository.findById(1L).orElseThrow();

        employeeRepository.delete(employee);

        Employee employeeNull = null;

        var optionalEmployee = Optional.ofNullable(employeeRepository.findByName("Martin"));

        if (optionalEmployee.isPresent()) {
            employeeNull = optionalEmployee.orElseThrow();
        }

        Assertions.assertThat(employeeNull).isNull();
    }

}
