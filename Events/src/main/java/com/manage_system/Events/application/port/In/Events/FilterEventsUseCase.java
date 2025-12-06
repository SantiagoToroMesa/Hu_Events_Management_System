package com.manage_system.Events.application.port.In.Events;

import com.manage_system.Events.Domain.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface FilterEventsUseCase {
    List<Event> filterEvents(Integer venueId, LocalDateTime start, LocalDateTime end);
}
