package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.domain.EmployeePassport;
import com.example.javaadv_task_5.repository.EmployeePassportRepository;
import com.example.javaadv_task_5.repository.EmployeeRepository;
import com.example.javaadv_task_5.repository.WorkPlaceRepository;
import com.example.javaadv_task_5.service.email_sender.EmailPattern;
import com.example.javaadv_task_5.service.email_sender.EmailSenderService;
import com.example.javaadv_task_5.util.annotations.entity.ActivateCustomAnnotations;
import com.example.javaadv_task_5.util.annotations.entity.Name;
import com.example.javaadv_task_5.util.annotations.entity.ToLowerCase;
import com.example.javaadv_task_5.util.exception.OneToOneRelationException;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import com.example.javaadv_task_5.util.exception.ResourceWasDeletedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final WorkPlaceRepository workPlaceRepository;
    private final EmployeePassportRepository employeePassportRepository;
    private final EmailSenderService emailSenderService;

    private final EmployeeWorkPlaceService employeeWorkPlaceService;

    @PersistenceContext
    private final EntityManager entityManager;
    private static final Long EMPLOYEE_MAX_WORK_PLACES_CNT = 3L;

    public EmployeeServiceBean(EmployeeRepository employeeRepository,
            WorkPlaceRepository workPlaceRepository,
            EmployeePassportRepository employeePassportRepository,
            EmployeeWorkPlaceService employeeWorkPlaceService,
        EmailSenderService emailSenderService,
        EntityManager entityManager) {
        this.employeeRepository = employeeRepository;
        this.workPlaceRepository = workPlaceRepository;
        this.employeeWorkPlaceService = employeeWorkPlaceService;
        this.employeePassportRepository = employeePassportRepository;
        this.emailSenderService = emailSenderService;
        this.entityManager = entityManager;
    }

    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
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
    public Employee getById(Long id) {
        return findByIdPreviously(id).get();
    }

    @Override
    public Employee updateNameById(Long id, String name) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setName(name);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public Employee updateEmailById(Long id, String email) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setEmail(email);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public Employee updateCountryById(Long id, String country) {
        return this.findByIdPreviously(id).map(entity -> {
            entity.setCountry(country);
            return employeeRepository.save(entity);
        }).get();
    }

    @Override
    public List<Employee> getByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public void removeById(Long id) {
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
    public List<Employee> sendEmailsByCountry(String country) {
        List<Employee> employees = employeeRepository.findByCountry(country);
        return employees.stream()
            .filter(e -> e.getEmail() != null)
            .map(e -> {
                String email = e.getEmail();
                String userName = e.getName();
                emailSenderService.send(email, userName, EmailPattern.form());
                return e;
            }).collect(Collectors.toList());
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

    @Override
    public Employee handPassport(Long employeeId, Long passportId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(ResourceNotFoundException::new);
        EmployeePassport employeePassport = employeePassportRepository.findById(passportId)
            .orElseThrow(ResourceNotFoundException::new);
        if (employeePassport.isHanded()) {
            throw new OneToOneRelationException();
        }
        if (employeePassport.getPreviousPassportId() != null) {
            throw new OneToOneRelationException();
        }
        employeePassport.hand();
        employeePassportRepository.save(employeePassport);
        employee.setWorkPass(employeePassport);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee deprivePassport(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(ResourceNotFoundException::new);
        EmployeePassport employeePassport = employee.getWorkPass();
        if (employeePassport != null) {
            employeePassport.deprive();
            employeePassport.setPreviousPassportId(employeePassport.getId());
            employeePassportRepository.save(employeePassport);
            employee.setWorkPass(null);
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesWithSeveralPassports(List<EmployeePassport> passports) {
        return employeeRepository.findAll().stream()
            .filter(e -> passports.contains(e.getWorkPass().getPreviousPassportId())).toList();
    }

    @Override
    public Employee takeWorkPlace(Long employeeId, Long workPlaceId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(ResourceNotFoundException::new);
        WorkPlace workPlace = workPlaceRepository.findById(workPlaceId)
            .orElseThrow(ResourceNotFoundException::new);
        if (employee.getWorkPlaces().stream().noneMatch(ewp -> workPlace.getId() == ewp.getWorkPlace()
            .getId())) {
            employeeWorkPlaceService.createByStoredProcedure(employeeId, workPlaceId);
        }
        return employeeWorkPlaceService.activate(employee, workPlace);
    }

    @Override
    public Employee freeWorkPlace(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(ResourceNotFoundException::new);
        return employeeWorkPlaceService.deactivate(employee);
    }

    private Optional<Employee> findByIdPreviously(Long id) {
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
