package com.manage_system.Events.Domain.exception;

public class DuplicateEventException extends DomainValidationException {
    public DuplicateEventException(String message) {
        super(message);
    }
}
