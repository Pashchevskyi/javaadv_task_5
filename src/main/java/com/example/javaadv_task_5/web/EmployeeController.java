package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.dto.EmployeeCountryDto;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeEmailDto;
import com.example.javaadv_task_5.dto.EmployeeOnlyDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import com.example.javaadv_task_5.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper;

    public EmployeeController(EmployeeService employeeService, ObjectMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }

    //Операция сохранения юзера в базу данных
    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployeeWithMapping(@RequestBody @Valid EmployeeDto requestForSave) {

        //Employee employee = mapper.employeeDtoToEmployee(requestForSave);
        Employee employee = mapper.convertValue(requestForSave, Employee.class);
        EmployeeDto dto = mapper.convertValue(employeeService.create(employee), EmployeeDto.class);
        //mapper.employeeToEmployeeDto(employeeService.create(employee));

        return dto;
    }

    @Override
    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.convertValue(employeeDto, Employee.class);
        employeeService.create(employee);
    }

    //Получение списка юзеров
    @Override
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        List<Employee> employees = employeeService.getAll();
        employees.forEach(erd -> employeesReadDto.add(
            //mapper.employeeToEmployeeReadDto(erd)
            mapper.convertValue(erd, EmployeeReadDto.class)
        ));
        return employeesReadDto;
    }

    @Override
    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging).map(
            //mapper::employeeToEmployeeReadDto
            e -> mapper.convertValue(e, EmployeeReadDto.class)
        );
    }

    //Получения юзера по id
    @Override
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return mapper.convertValue(employee, EmployeeReadDto.class);
        //mapper.employeeToEmployeeReadDto(employee);
    }

    @Override
    @PatchMapping("/users/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshName(@PathVariable Long id, @RequestBody EmployeeDto eDto) {
        return mapper.convertValue(employeeService.updateNameById(id, eDto.name()), EmployeeDto.class);
        //mapper.employeeToEmployeeDto(employeeService.updateNameById(id, eDto.getName()));
    }

    @Override
    @PatchMapping("/users/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmail(@PathVariable Long id, @RequestBody EmployeeDto eDto) {
        return mapper.convertValue(employeeService.updateEmailById(id, eDto.email()), EmployeeDto.class);
            //mapper.employeeToEmployeeDto(employeeService.updateEmailById(id, eDto.getEmail()));
    }

    @Override
    @PatchMapping("/users/{id}/country")
    public EmployeeDto refreshCountry(@PathVariable Long id, @RequestBody EmployeeDto eDto) {
        return mapper.convertValue(employeeService.updateCountryById(id, eDto.country()), EmployeeDto.class);
            //mapper.employeeToEmployeeDto(employeeService.updateCountryById(id, eDto.getCountry()));
    }

    @Override
    @GetMapping("/users/email")
    public List<EmployeeOnlyDto> getEmployeesByEmail(EmployeeEmailDto employeeEmailDto) {
        List<EmployeeOnlyDto> list = new ArrayList<>();
        employeeService.getByEmail(employeeEmailDto.email())
            .forEach(e -> list.add(mapper.convertValue(e, EmployeeOnlyDto.class)));
        return list;
    }


    @Override
    @GetMapping("/users/no-email")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeByEmailNull() {
        List<EmployeeReadDto> list = new ArrayList<>();
        employeeService.getByEmailNull()
            .forEach(e -> list.add(
                mapper.convertValue(e, EmployeeReadDto.class))
                //mapper.employeeToEmployeeReadDto(e))
            );

        return list;
    }

    @Override
    @PatchMapping("/users/fix-countries")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> fixCountriesNames() {
        List<EmployeeReadDto> list = new ArrayList<>();
        employeeService.getByCountryStartingWithLowercase()
            .forEach(e -> list.add(
                mapper.convertValue(e, EmployeeReadDto.class)
                //mapper.employeeToEmployeeReadDto(e)
            ));
        employeeService.setCountryFirstLetterCapitalized();

        return list;
    }


    //Удаление по id
    @Override
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Long id) {
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
    @GetMapping("/users/send-emails")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> sendEmails(@RequestBody @Valid EmployeeCountryDto employeeCountryDto) {
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        List<Employee> list = employeeService.sendEmailsByCountry(employeeCountryDto.country());
        list.forEach(e -> employeesReadDto.add(mapper.convertValue(e, EmployeeReadDto.class)));
        return employeesReadDto;
    }

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
            sortOrder.toString()).map(
                //mapper::employeeToEmployeeReadDto
            e -> mapper.convertValue(e, EmployeeReadDto.class)
        );
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
        employees.forEach(erd -> employeesReadDto.add(
            //mapper.employeeToEmployeeReadDto(erd)
            mapper.convertValue(erd, EmployeeReadDto.class)
        ));
        return employeesReadDto;
    }

    @Override
    @PatchMapping("/users/{employeeId}/passports/{passportId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto handPassport(@PathVariable Long employeeId, @PathVariable Long passportId) {
        return mapper.convertValue(employeeService.handPassport(employeeId, passportId), EmployeeReadDto.class);
    }

    @Override
    @PatchMapping("/users/{employeeId}/deprive")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto deprivePassport(@PathVariable Long employeeId) {
        return mapper.convertValue(employeeService.deprivePassport(employeeId), EmployeeReadDto.class);
    }

    @Override
    @PatchMapping("/users/{employeeId}/workplaces/{workPlaceId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto takeWorkPlace(@PathVariable Long employeeId, @PathVariable Long workPlaceId) {
        return mapper.convertValue(employeeService.takeWorkPlace(employeeId, workPlaceId), EmployeeReadDto.class);
            //mapper.employeeToEmployeeReadDto(employeeService.addWorkPlace(employeeId, workPlaceId));
    }

    @Override
    @PatchMapping("/users/{employeeId}/workplaces")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto freeWorkPlace(@PathVariable Long employeeId) {
        return mapper.convertValue(employeeService.freeWorkPlace(employeeId), EmployeeReadDto.class);
            //mapper.employeeToEmployeeReadDto(employeeService.removeWorkPlace(employeeId, workPlaceId));
    }
}
