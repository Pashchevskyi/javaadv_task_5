package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.EmployeeCountryDto;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeEmailDto;
import com.example.javaadv_task_5.dto.EmployeeOnlyDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Employee", description = "Employee API")
public interface EmployeeDocumentable {
  @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
      @ApiResponse(responseCode = "409", description = "Employee already exists")})
  EmployeeDto saveEmployeeWithMapping(@RequestBody @Valid EmployeeDto requestForSave);

  @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
      @ApiResponse(responseCode = "409", description = "Employee already exists")})
  EmployeeReadDto getEmployeeById(@PathVariable Long id);

  @Operation(summary = "This endpoint returns a list of employees.", description = "Create request to read a list of employees", tags = {"List", "Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the list of employees")
  })
  List<EmployeeReadDto> getAllUsers();

  @Operation(summary = "The endpoint returns a paginated list of employees.", description = "Create request to read a paginated list of employees", tags = {"Page", "Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the paginated list of employees")
  })
  Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size);
  @Operation(summary = "The endpoint updates the name of Employee and returns the updated Employee.", description = "Create request to update the name of Employee", tags = {"Name", "Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the updated Employee."),
      @ApiResponse(responseCode = "404", description = "Employee not found"),
      @ApiResponse(responseCode = "403", description = "Permission denied")
  })
  EmployeeDto refreshName(@PathVariable Long id, @RequestBody EmployeeDto eDto);

  @Operation(summary = "The endpoint updates the e-mail of Employee and returns the updated Employee.", description = "Create request to update the e-mail of Employee", tags = {"E-mail", "Email", "Mail", "Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the updated Employee."),
      @ApiResponse(responseCode = "404", description = "Employee not found"),
      @ApiResponse(responseCode = "403", description = "Permission denied")
  })
  EmployeeDto refreshEmail(@PathVariable Long id, @RequestBody EmployeeDto eDto);

  @Operation(summary = "The endpoint updates the country of Employee and returns the updated Employee.", description = "Create request to update the country of Employee", tags = {"Country", "Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the updated Employee."),
      @ApiResponse(responseCode = "404", description = "Employee not found"),
      @ApiResponse(responseCode = "403", description = "Permission denied")
  })
  EmployeeDto refreshCountry(@PathVariable Long id, @RequestBody EmployeeDto eDto);

  @Operation(summary = "This is endpoint returns list of employees by their E-mail.", description = "Create request to read an employees by e-mail", tags = {"Employee", "E-mail", "Email", "Mail"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list.")
  })
  List<EmployeeOnlyDto> getEmployeesByEmail(@RequestBody EmployeeEmailDto employeeEmailDto);
  @Operation(summary = "This is endpoint returns an employees with no E-mail.", description = "Create request to read an employee with no e-mail", tags = {"Employee", "No", "E-mail", "Email", "Mail"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list.")
  })
  List<EmployeeReadDto> getEmployeeByEmailNull();
  @Operation(summary = "This is endpoint fixes the names of countries. Returns list of employees with old countries names.", description = "Create request to fix an employees with incorrect countries names.", tags = {"Employee", "Country"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list."),
      @ApiResponse(responseCode = "401", description = "Unauthorized.")
  })
  List<EmployeeReadDto> fixCountriesNames();
  @Operation(summary = "This is endpoint removes an Employee by his id.", description = "Create request to remove an Employee by id", tags = {"Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No content (Employee removed successfully)"),
      @ApiResponse(responseCode = "401", description = "Unauthorized."),
      @ApiResponse(responseCode = "403", description = "Permission denied"),
      @ApiResponse(responseCode = "404", description = "Employee not found"),
      @ApiResponse(responseCode = "405", description = "Method not allowed")
  })
  void removeEmployeeById(@PathVariable Long id);
  @Operation(summary = "This is endpoint removes all employees.", description = "Create request to remove all employees", tags = {"Employee"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No content (Employees removed successfully)"),
      @ApiResponse(responseCode = "401", description = "Unauthorized."),
      @ApiResponse(responseCode = "403", description = "Permission denied"),
      @ApiResponse(responseCode = "405", description = "Method not allowed")
  })
  void removeAllUsers();

  @Operation(summary = "This is endpoint returns paginated list of employees by their countries.", description = "Create request to read an employees by countries", tags = {"Employee", "Country"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list.")
  })
  Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "") List<String> sortList,
      @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);
  @Operation(summary = "This is endpoint returns list of countries names of employees.", description = "Create request to read countries names of employees", tags = {"Employee", "Country", "Name"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the countries names list.")
  })
  List<String> getAllUsersC();
  @Operation(summary = "This is endpoint returns list of employees sort.", description = "Create request to read list of employees sort", tags = {"Employee", "Sort"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the countries names list.")
  })
  List<String> getAllUsersSort();
  @Operation(summary = "This is endpoint returns list of employees emails.", description = "Create request to read list of employees emails", tags = {"Employee", "E-mail", "Email", "Mail"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the countries names list.")
  })
  Optional<String> getAllUsersSo();
  @Operation(summary = "This is endpoint returns list of employees by their countries.", description = "Create request to read an employees by countries", tags = {"Employee", "Country"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list.")
  })
  List<EmployeeReadDto> getByCountry(@RequestParam(required = true) String country);
  @Operation(summary = "This is endpoint sends email to employees and returns list of they.", description = "Create request to send email to employees and return list of they.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the employees list."),
      @ApiResponse(responseCode = "401", description = "Unauthorized.")
  })
  List<EmployeeReadDto> sendEmails(@RequestBody @Valid EmployeeCountryDto employeeCountryDto);
  @Operation(summary = "This endpoint assigns passport to employee.", description = "Create request to assign passport to employee.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Passport has been handed to Employee."),
      @ApiResponse(responseCode = "400", description = "Passport has been already handled to another Employee"),
      @ApiResponse(responseCode = "403", description = "You are not authorized to hand Passport"),
      @ApiResponse(responseCode = "404", description = "Employee or Passport has not been found")
  })
  EmployeeReadDto handPassport(@PathVariable Long employeeId, @PathVariable Long passportId);
  @Operation(summary = "This endpoint detaches passport from employee.", description = "Create request to detach passport from employee.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Passport has been detached from Employee."),
      @ApiResponse(responseCode = "403", description = "You are not authorized to hand Passport"),
      @ApiResponse(responseCode = "404", description = "Employee has not been found")
  })
  EmployeeReadDto deprivePassport(@PathVariable Long employeeId);

  @Operation(summary = "The endpoint makes workplace active for employee.", description = "Make workplace active for employee.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Workplace has been made active"),
      @ApiResponse(responseCode = "403", description = "Forbidden to create more than 3 workplaces for the same employee"),
      @ApiResponse(responseCode = "404", description = "Employee and/or workplace not found")
  })
  EmployeeReadDto takeWorkPlace(@PathVariable Long employeeId, @PathVariable Long workPlaceId);
  @Operation(summary = "The endpoint makes workplace inactive for employee.", description = "Make workplace inactive for employee.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Workplace has been made inactive"),
      @ApiResponse(responseCode = "404", description = "Employee not found")
  })
  EmployeeReadDto freeWorkPlace(@PathVariable Long employeeId);
}
