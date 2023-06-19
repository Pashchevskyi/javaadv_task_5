package com.example.javaadv_task_5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.Gender;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import com.example.javaadv_task_5.service.EmployeeServiceBean;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class ServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceBean service;

    private Employee employee;

    private List<Employee> employeesWithNoEmail = new ArrayList<>();

    private List<Employee> employeesFromLowerCasedCountry = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Mark")
                .country("UK")
                .email("test@mail.com")
                .gender(Gender.M)
                .build();
        employeesWithNoEmail.add(Employee
            .builder()
            .id(2)
            .name("German")
            .country("Ukraine")
            .gender(Gender.M)
            .build());
        employeesWithNoEmail.add(Employee
            .builder()
            .id(3)
            .name("Anna")
            .country("Germany")
            .gender(Gender.F)
            .build());
        employeesFromLowerCasedCountry.add(Employee
            .builder()
            .id(4)
            .name("Ivan")
            .country("russia")
            .email("vanya@mira.net")
            .build());
        employeesFromLowerCasedCountry.add(Employee
            .builder()
            .id(5)
            .name("Zinaida")
            .country("russia")
            .email("zina@mira.net")
            .build());
    }

    @Test
    @DisplayName("Save employee test")
    public void whenSaveEmployee_shouldReturnEmployee() {

        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        var created = service.create(employee);
        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Get employee by exist id test")
    public void whenGivenId_shouldReturnEmployee_ifFound() {

        Employee employee = new Employee();
        employee.setId(88);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {

        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

    @Test
    @DisplayName("Get employees with no email test")
    public void getEmailNullTest() {
        when(employeeRepository.findByEmailNull()).thenReturn(employeesWithNoEmail);
        List<Employee> employees = service.getByEmailNull();
        assertThat(employees).isEqualTo(employeesWithNoEmail);
    }

    @Test
    @DisplayName("Get employees from lower-cased country test")
    public void getByCountryStartingWithLowercaseTest() {
        when(employeeRepository.findByCountryStartingWithLowercase())
            .thenReturn(employeesFromLowerCasedCountry);
        List<Employee> employees = service.getByCountryStartingWithLowercase();
        assertThat(employees).containsAll(employeesFromLowerCasedCountry);
    }

    @Test
    @DisplayName("Set country the first letter capitalized test")
    void setCountryFirstLetterCapitalizedTest() {
        when(service.getByCountryStartingWithLowercase())
            .thenReturn(employeesFromLowerCasedCountry);
        service.setCountryFirstLetterCapitalized();
        employeeRepository.findByCountryStartingWithLowercase()
            .forEach(e -> assertThat(e.getCountry().charAt(0)).isUpperCase());
    }

    @Test
    @DisplayName("Read employee by id test")
    public void readEmployeeByIdTest() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Read all employees test")
    public void readAllEmployeesTest() {

        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        var list = employeeRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Delete employee test")
    public void deleteEmployeeTest() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        service.removeById(employee.getId());
        assertThat(employee.getIsDeleted()).isEqualTo(true);
    }
}
