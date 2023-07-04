package com.example.javaadv_task_5.util.annotations.dto;

import java.util.Arrays;
import java.util.Locale;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryRightFormedValidator implements ConstraintValidator<CountryRightFormed, String> {

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null)
            return true;

        return Arrays.asList(Locale.getISOCountries()).contains(country);
    }
}
