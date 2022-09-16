package ru.vsu.csf.asashina.cinemaback.annotations;

import ru.vsu.csf.asashina.cinemaback.validators.DurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Duration {

    String message() default "Invalid time format (HH:MM or H:MM or in minutes))";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
