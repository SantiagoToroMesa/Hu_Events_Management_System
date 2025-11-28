package com.manage_system.Events.application.port.Out.Events;

import com.manage_system.Events.Domain.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> getEventById(Integer id);
    List<Event> getAllEvents();
    void delete(Integer id);
    Event update(Integer id, Event event);
    boolean existsByTitle(String title);
}
