package com.example.javaadv_task_5.dto;

import com.example.javaadv_task_5.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeDto {

    public Integer id;

    @NotNull
    @Size(min = 1, max = 64, message = "Name must be between 1 and 64 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,19}(\\s[A-Z]{1}[a-z]{1,43})?$")
    public String name;

    @Schema(description = "Name of the country.", example = "England", required = true)
    @Pattern(regexp = "^(The\\s)?(United\\s)?([A-Z]{1}[a-z]{1,7}){1,2}(\\s?(of)?\\s?[A-Z]{1}[a-z]{1,7})?\\s?([A-Z]{1}[a-z]{1,7})?(\\s?(and)?\\s?[A-Z]{1}[a-z]{1,7})?\\s?([A-Z]{1}[a-z]{1,7})?$")
    public String country;

    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    @PastOrPresent
    public Instant startDate = Instant.now();

    //public Set<AddressDto> addresses = new HashSet<>();

    public Gender gender;

    public Set<AddressDto> addresses = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }
}
