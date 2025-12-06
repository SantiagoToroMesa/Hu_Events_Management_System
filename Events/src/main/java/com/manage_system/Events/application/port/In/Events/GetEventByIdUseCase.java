package com.manage_system.Events.application.port.In.Events;

import com.manage_system.Events.Domain.model.Event;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface GetEventByIdUseCase {
    Event getEventById(Integer id) throws EntityNotFoundException;
}
