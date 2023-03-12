package com.maersk.bookingvalidate.validator;

import static java.util.stream.Collectors.toSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;

public class OneOfValidator implements ConstraintValidator<OneOf, Integer> {

    private Set<Integer> allowedValues;

    @Override
    public void initialize(OneOf constraintAnnotation) {
        allowedValues = Arrays.stream(constraintAnnotation.value()).boxed().collect(toSet());
    }

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        return i == null || allowedValues.contains(i);
    }
}
