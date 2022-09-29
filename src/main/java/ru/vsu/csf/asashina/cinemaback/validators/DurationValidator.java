package ru.vsu.csf.asashina.cinemaback.validators;

import ru.vsu.csf.asashina.cinemaback.annotations.Duration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class DurationValidator implements ConstraintValidator<Duration, LocalTime> {

    @Override
    public void initialize(Duration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localTime == null) {
            return false;
        }
        return localTime.isAfter(LocalTime.of(0, 0)) && localTime.isBefore(LocalTime.of(7, 0));
    }
}
