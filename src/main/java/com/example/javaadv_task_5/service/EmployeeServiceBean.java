package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import com.example.javaadv_task_5.util.exception.ResourceWasDeletedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
   // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEM(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public List<Employee> getByEmailNull() {
        return employeeRepository.findByEmailNull().stream().filter(e -> !e.getIsDeleted())
            .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getByCountryStartingWithLowercase() {
        return employeeRepository.findByCountryStartingWithLowercase().stream()
            .filter(e -> !e.getIsDeleted()).collect(Collectors.toList());
    }

    @Override
    public void setCountryFirstLetterCapitalized() {
        List<Employee> employees = getByCountryStartingWithLowercase();
        employees.forEach(e -> {
            String country = e.getCountry();
            String updatedCountry = country.substring(0, 1).toUpperCase() + country
                .substring(1).toLowerCase();
            e.setCountry(updatedCountry);
            employeeRepository.save(e);
        });
    }


    @Override
    public Employee getById(Integer id) {
        return findByIdPreviously(id).get();
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        return this.findByIdPreviously(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return employeeRepository.save(entity);
                }).get();
    }

    @Override
    public void removeById(Integer id) {
        var employee = this.findByIdPreviously(id).get();
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.findAll().forEach(e -> {
            if (!e.getIsDeleted()) {
                e.setIsDeleted(true);
                employeeRepository.save(e);
            }
        });

    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findByCountry(country);
    }

    private Optional<Employee> findByIdPreviously(Integer id) {
        try {
            var employee = employeeRepository.findById(id);
            if (employee.get().getIsDeleted()) {
                throw new ResourceWasDeletedException();
            }
            return employee;
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException();
        }
    }
}
