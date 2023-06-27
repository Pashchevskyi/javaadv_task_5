package com.example.javaadv_task_5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record EmployeeEmailDto(
    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    String email) {
}
