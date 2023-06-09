package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import com.example.javaadv_task_5.util.exception.ResourceWasDeletedException;
import java.util.NoSuchElementException;
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

@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceBean(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getById(Integer id) {
        return findByIdPreviously(id).get();
    }

    @Override
    public Employee updateNameById(Integer id, String name) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setName(name);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public Employee updateEmailById(Integer id, String email) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setEmail(email);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public Employee updateCountryById(Integer id, String country) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setCountry(country);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = this.findByIdPreviously(id).get();
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
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());

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
        List<Employee> employeeList = employeeRepository.findAll();

        List<String> emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        String opt = emails.stream()
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
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.get().getIsDeleted()) {
                throw new ResourceWasDeletedException();
            }
            return employee;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException();
        }
    }
}
