package com.manage_system.Events.Domain.service;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.exception.DuplicateEventException;
import com.manage_system.Events.application.port.Out.Events.EventRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class EventValidationService {

    private final EventRepositoryPort repository;

    public EventValidationService(EventRepositoryPort repository) {
        this.repository = repository;
    }

    public void validateNoDuplicate(Event event) {

        boolean exists = repository.existsByTitle(event.title());

        if (exists) {
            throw new DuplicateEventException(
                    "An event with title '" + event.title() + "' already exists."
            );
        }
    }
}
