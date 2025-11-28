package com.manage_system.Events.application.port.In.Events;

import com.manage_system.Events.Domain.model.Event;

public interface CreateEventUseCase {
    Event createEvent(Event event);
}
