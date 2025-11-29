package com.manage_system.Events.application.port.In.Events;

import com.manage_system.Events.Domain.model.Event;

import java.util.List;

public interface GetAllEventsUseCase {
    List<Event> getAllEvents();
}
