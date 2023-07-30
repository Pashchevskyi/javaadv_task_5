package com.example.javaadv_task_5.web.api;

import com.example.javaadv_task_5.dto.PhotoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Photo", description = "Photo API")
public interface PhotoDocumentable {
  @Operation(summary = "This is endpoint to add a new photo.", description = "Create request to add a new photo.", tags = {"Photo"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "CREATED. The new photo is successfully created and added to database."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified photo request not found."),
      @ApiResponse(responseCode = "409", description = "Photo already exists")})
  PhotoDto create(PhotoDto photoDto);
  @Operation(summary = "This endpoint returns a list of photos.", description = "Create request to read a list of photos", tags = {"List", "Photo"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK. Here is the list of photos")
  })
  List<PhotoDto> readAll();
  @Operation(summary = "This is endpoint returned a photo by his id.", description = "Create request to read a photo by id", tags = {"Photo"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
      @ApiResponse(responseCode = "400", description = "Invalid input"),
      @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified photo request not found."),
      @ApiResponse(responseCode = "409", description = "Photo already exists")
  })
  PhotoDto read(Long id);
}
