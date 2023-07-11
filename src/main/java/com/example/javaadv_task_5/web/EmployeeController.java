package com.example.javaadv_task_5.web;

import com.example.javaadv_task_5.domain.Employee;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import com.example.javaadv_task_5.service.EmployeeService;
import com.example.javaadv_task_5.util.config.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

        Employee employee = mapper.employeeDtoToEmployee(requestForSave);
        EmployeeDto dto = mapper.employeeToEmployeeDto(employeeService.create(employee));

        return dto;
    }
    @PostMapping("/usersS")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmployee1(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.employeeDtoToEmployee(employeeDto);
        employeeService.create(employee);
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        List<Employee> employees = employeeService.getAll();
        employees.forEach(erd -> employeesReadDto.add(mapper.employeeToEmployeeReadDto(erd)));
        return employeesReadDto;
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging).map(mapper::employeeToEmployeeReadDto);
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return mapper.employeeToEmployeeReadDto(employee);
    }

    @PatchMapping("/users/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshName(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateNameById(id, eDto.getName()));
    }

    @PatchMapping("/users/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmail(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateEmailById(id, eDto.getEmail()));
    }

    @PatchMapping("/users/{id}/country")
    public EmployeeDto refreshCountry(@PathVariable Integer id, @RequestBody EmployeeDto eDto) {
        return mapper.employeeToEmployeeDto(employeeService.updateCountryById(id, eDto.getCountry()));
    }

    @GetMapping("/users/no-email")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeByEmailNull() {
        List<EmployeeReadDto> list = new ArrayList<>();
        employeeService.getByEmailNull().forEach(e -> list.add(mapper.employeeToEmployeeReadDto(e)));

        return list;
    }

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
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
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
            sortOrder.toString()).map(mapper::employeeToEmployeeReadDto);
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

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

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getByCountry(@RequestParam(required = true) String country) {
        List<Employee> employees = employeeService.filterByCountry(country);
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        employees.forEach(erd -> employeesReadDto.add(mapper.employeeToEmployeeReadDto(erd)));
        return employeesReadDto;
    }

    @PatchMapping("/users/{employeeId}/workplaces/{workPlaceId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addWorkPlace(@PathVariable Integer employeeId, @PathVariable Integer workPlaceId) {
        return mapper.employeeToEmployeeReadDto(employeeService.addWorkPlace(employeeId, workPlaceId));
    }

    @PatchMapping("/users/{employeeId}/workplaces-removing/{workPlaceId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto removeWorkPlace(@PathVariable Integer employeeId, @PathVariable Integer workPlaceId) {
        return mapper.employeeToEmployeeReadDto(employeeService.removeWorkPlace(employeeId, workPlaceId));
    }
}
