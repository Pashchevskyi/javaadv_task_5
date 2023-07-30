package com.example.javaadv_task_5.dto;

import com.example.javaadv_task_5.util.annotations.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeCountryDto(
    @Schema(description = "Name of the country.", example = "England", required = true)
    @CountryRightFormed
    String country) {
}
