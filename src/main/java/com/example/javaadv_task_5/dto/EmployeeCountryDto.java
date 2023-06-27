package com.example.javaadv_task_5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;

public record EmployeeCountryDto(
    @Schema(description = "Name of the country.", example = "England", required = true)
    @Pattern(regexp = "^(The\\s)?(United\\s)?([A-Z]{1}[a-z]{1,9}){1,2}(\\s?(of)?\\s?[A-Z]{1}[a-z]{1,7})?\\s?([A-Z]{1}[a-z]{1,7})?(\\s?(and)?\\s?[A-Z]{1}[a-z]{1,7})?\\s?([A-Z]{1}[a-z]{1,7})?$")
    String country) {
}
