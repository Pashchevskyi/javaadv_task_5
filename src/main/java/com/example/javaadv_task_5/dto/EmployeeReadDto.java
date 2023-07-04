package com.example.javaadv_task_5.dto;

import com.example.javaadv_task_5.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public record EmployeeReadDto(
    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    String name,
    String country,
    @Email
    @NotNull
    String email,
    Set<AddressDto> addresses,
    Date date,
    Gender gender
) {
}
