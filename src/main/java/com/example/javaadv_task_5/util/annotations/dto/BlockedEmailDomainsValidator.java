package com.example.javaadv_task_5.util.annotations.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BlockedEmailDomainsValidator implements ConstraintValidator<BlockedEmailDomains, String> {

    private String[] domains;
    private String[] blockedWords;

    @Override
    public void initialize(BlockedEmailDomains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        domains = constraintAnnotation.contains();
        blockedWords = constraintAnnotation.blockedWords();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null)
            return true;
        boolean containsBlockedWord = Arrays.stream(blockedWords)
            .anyMatch(w -> email.toLowerCase().contains(w));
        boolean containsBlockedDomain = Arrays.stream(domains).anyMatch(email::endsWith);

        return !containsBlockedWord && !containsBlockedDomain;
    }
}
