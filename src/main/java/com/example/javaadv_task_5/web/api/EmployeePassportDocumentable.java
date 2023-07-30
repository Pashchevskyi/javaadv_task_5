package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.EmployeePassportDto;
import com.example.javaadv_task_5.dto.EmployeePassportReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "EmployeePassport", description = "Employee passport API")
public interface EmployeePassportDocumentable {
  @Operation(summary = "This is endpoint to add a new employee passport.", description = "Create request to add a new passport for employee.", tags = {"EmployeePassport", "Passport"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "CREATED. The new employee passport is successfully created and added to database."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee passport request not found."),
      @ApiResponse(responseCode = "409", description = "EmployeePassport already exists")})
  EmployeePassportDto create(EmployeePassportDto employeePassportDto);

  @Operation(summary = "This endpoint returns a list of passports.", description = "Create request to read a list of passports", tags = {"List", "EmployeePassport", "Passport"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the list of passports")
  })
  List<EmployeePassportReadDto> readAll();

  @Operation(summary = "This is endpoint returned a passport by its id.", description = "Create request to read a passport by id", tags = {"EmployeePassport", "Passport"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified passport request not found."),
      @ApiResponse(responseCode = "409", description = "Passport already exists")})
  EmployeePassportReadDto read(Long id);

  @Operation(summary = "This is endpoint removes an Passport by its id.", description = "Create request to remove a Passport by id", tags = {"Passport"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "No content (Passport removed successfully)"),
      @ApiResponse(responseCode = "401", description = "Unauthorized."),
      @ApiResponse(responseCode = "403", description = "Permission denied"),
      @ApiResponse(responseCode = "404", description = "Passport not found"),
      @ApiResponse(responseCode = "405", description = "Method not allowed")
  })
  List<EmployeePassportReadDto> delete(Long id);
  @Operation(summary = "This endpoint updates photo in passport.", description = "Create request to update photo in passport.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Photo has been updated."),
      @ApiResponse(responseCode = "400", description = "Photo has been already attached to another Passport"),
      @ApiResponse(responseCode = "403", description = "You are not authorized to update Photo"),
      @ApiResponse(responseCode = "404", description = "Photo or Passport has not been found")
  })
  EmployeePassportReadDto updatePhoto(Long passportId, Long photoId);

  @Operation(summary = "This endpoint deletes photo from passport.", description = "Create request to delete photo from passport.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Photo has been deleted from Passport."),
      @ApiResponse(responseCode = "403", description = "You are not authorized to attach Photo"),
      @ApiResponse(responseCode = "404", description = "Photo has not been found")
  })
  EmployeePassportReadDto detachPhoto(Long passportId);
}
