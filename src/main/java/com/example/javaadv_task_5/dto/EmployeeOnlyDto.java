package com.example.javaadv_task_5.dto;

import com.example.javaadv_task_5.domain.Gender;
import com.example.javaadv_task_5.util.annotations.dto.BlockedEmailDomains;
import com.example.javaadv_task_5.util.annotations.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record EmployeeOnlyDto(
    Integer id,
    @NotNull
    @Size(min = 1, max = 64, message = "Name must be between 1 and 64 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,19}(\\s[A-Z]{1}[a-z]{1,43})?$")
    String name,
    @Schema(description = "Name of the country.", example = "England", required = true)
    @CountryRightFormed
    String country,
    @Email
    @NotNull
    @BlockedEmailDomains
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    String email,
    @PastOrPresent
    Date startDate,
    Gender gender
) {
}
