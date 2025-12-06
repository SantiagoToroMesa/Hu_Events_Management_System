package com.manage_system.Events.Infrastucture.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRangeValid {
    String message() default "{validation.daterange.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
