package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.EmployeeCountryDto;
import com.example.javaadv_task_5.dto.EmployeeDto;
import com.example.javaadv_task_5.dto.EmployeeEmailDto;
import com.example.javaadv_task_5.dto.EmployeeOnlyDto;
import com.example.javaadv_task_5.dto.EmployeeReadDto;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmployeeControllable {
  EmployeeDto saveEmployeeWithMapping(@RequestBody @Valid EmployeeDto requestForSave);
  void saveEmployee(@RequestBody EmployeeDto employeeDto);
  List<EmployeeReadDto> getAllUsers();
  Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size
  );
  EmployeeReadDto getEmployeeById(@PathVariable Long id);
  EmployeeDto refreshName(@PathVariable Long id, @RequestBody EmployeeDto eDto);
  EmployeeDto refreshEmail(@PathVariable Long id, @RequestBody EmployeeDto eDto);
  EmployeeDto refreshCountry(@PathVariable Long id, @RequestBody EmployeeDto eDto);
  List<EmployeeOnlyDto> getEmployeesByEmail(@RequestBody EmployeeEmailDto employeeEmailDto);
  List<EmployeeReadDto> getEmployeeByEmailNull();
  List<EmployeeReadDto> fixCountriesNames();
  void removeEmployeeById(@PathVariable Long id);
  void removeAllUsers();
  Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "") List<String> sortList,
      @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);
  List<String> getAllUsersC();
  List<String> getAllUsersSort();
  Optional<String> getAllUsersSo();
  List<EmployeeReadDto> getByCountry(@RequestParam(required = true) String country);
  List<EmployeeReadDto> sendEmails(@RequestBody @Valid EmployeeCountryDto employeeCountryDto);
  EmployeeReadDto handPassport(@PathVariable Long employeeId, @PathVariable Long passportId);
  EmployeeReadDto deprivePassport(@PathVariable Long employeeId);
  EmployeeReadDto takeWorkPlace(@PathVariable Long employeeId, @PathVariable Long workPlaceId);
  EmployeeReadDto freeWorkPlace(@PathVariable Long employeeId);
}
