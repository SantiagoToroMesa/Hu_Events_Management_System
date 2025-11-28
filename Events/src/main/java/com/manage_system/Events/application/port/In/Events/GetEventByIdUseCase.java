package com.manage_system.Events.application.port.In.Events;

import com.manage_system.Events.Domain.model.Event;

import java.util.Optional;

public interface GetEventByIdUseCase {
    Optional<Event> getEventById(Integer id);
}
