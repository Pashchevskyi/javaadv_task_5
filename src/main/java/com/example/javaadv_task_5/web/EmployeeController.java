package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeEmailDto;
import com.example.javaadv_task_5.dto.EmployeeOnlyDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import com.example.javaadv_task_5.service.EmployeeService;
import com.example.javaadv_task_5.util.config.EmployeeMapper;
import com.example.javaadv_task_5.web.api.EmployeeControllable;
import com.example.javaadv_task_5.web.api.EmployeeDocumentable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController implements EmployeeControllable, EmployeeDocumentable {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Операция сохранения юзера в базу данных
    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployeeWithMapping(@RequestBody @Valid EmployeeDto requestForSave) {

        Employee employee = mapper.employeeDtoToEmployee(requestForSave);
        EmployeeDto dto = mapper.employeeToEmployeeDto(employeeService.create(employee));

        return dto;
    }

    @Override
    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.employeeDtoToEmployee(employeeDto);
        employeeService.create(employee);
    }

    //Получение списка юзеров
    @Override
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        List<Employee> employees = employeeService.getAll();
        employees.forEach(erd -> employeesReadDto.add(mapper.employeeToEmployeeReadDto(erd)));
        return employeesReadDto;
    }

    @Override
    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging).map(mapper::employeeToEmployeeReadDto);
    }

    //Получения юзера по id
    @Override
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return mapper.employeeToEmployeeReadDto(employee);
    }

    @Override
    @PatchMapping("/users/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshName(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateNameById(id, eDto.getName()));
    }

    @Override
    @PatchMapping("/users/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmail(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateEmailById(id, eDto.getEmail()));
    }

    @Override
    @PatchMapping("/users/{id}/country")
    public EmployeeDto refreshCountry(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateCountryById(id, eDto.getCountry()));
    }

    @Override
    @GetMapping("/users/email")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeOnlyDto> getEmployeesByEmail(@RequestBody EmployeeEmailDto employeeEmailDto) {
        List<EmployeeOnlyDto> list = new ArrayList<>();
        employeeService.getByEmail(employeeEmailDto.email)
            .forEach(e -> list.add(mapper.employeeToEmployeeOnlyDto(e)));
        return list;
    }

    @Override
    @GetMapping("/users/no-email")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeByEmailNull() {
        List<EmployeeReadDto> list = new ArrayList<>();
        employeeService.getByEmailNull().forEach(e -> list.add(mapper.employeeToEmployeeReadDto(e)));

        return list;
    }

    @Override
    @PatchMapping("/users/fix-countries")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> fixCountriesNames() {
        List<EmployeeReadDto> list = new ArrayList<>();
        employeeService.getByCountryStartingWithLowercase()
            .forEach(e -> list.add(mapper.employeeToEmployeeReadDto(e)));
        employeeService.setCountryFirstLetterCapitalized();

        return list;
    }


    //Удаление по id
    @Override
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @Override
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @Override
    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeService.findByCountryContaining(country, page, size, sortList,
            sortOrder.toString()).map(mapper::employeeToEmployeeReadDto);
    }

    @Override
    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @Override
    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @Override
    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getByCountry(@RequestParam(required = true) String country) {
        List<Employee> employees = employeeService.filterByCountry(country);
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        employees.forEach(erd -> employeesReadDto.add(mapper.employeeToEmployeeReadDto(erd)));
        return employeesReadDto;
    }
}
