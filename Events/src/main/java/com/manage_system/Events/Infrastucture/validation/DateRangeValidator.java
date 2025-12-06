package com.manage_system.Events.Infrastucture.validation;

import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRangeValid, EventCreateDto> {

    @Override
    public boolean isValid(EventCreateDto dto, ConstraintValidatorContext context) {

        if (dto.getStartAt() == null || dto.getEndAt() == null) {
            return true;
        }

        return dto.getStartAt().isBefore(dto.getEndAt());
    }
}
