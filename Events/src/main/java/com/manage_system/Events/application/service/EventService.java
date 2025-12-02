package com.manage_system.Events.application.service;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.service.EventValidationService;
import com.manage_system.Events.Infrastucture.mapper.EventMapper;
import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import com.manage_system.Events.application.port.In.Events.*;
import com.manage_system.Events.application.port.Out.Events.EventRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService implements CreateEventUseCase, GetEventByIdUseCase, GetAllEventsUseCase, DeleteEventUseCase, UpdateEventUseCase, FilterEventsUseCase {
    @Override
    public List<Event> filterEvents(Integer venueId, LocalDateTime start, LocalDateTime end) {
        return eventRepositoryPort.filterEvents(venueId, start, end);
    }

    private final EventRepositoryPort eventRepositoryPort;
    private final EventValidationService validationService;

    public EventService(EventRepositoryPort eventRepositoryPort, EventValidationService validationService) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.validationService = validationService;
    }

    @Override
    public Event createEvent(Event event) {
        validationService.validateNoDuplicate(event);
        return eventRepositoryPort.save(event);
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return eventRepositoryPort.getEventById(id);
    }


    @Override
    public List<Event> getAllEvents() {
        return eventRepositoryPort.getAllEvents();
    }

    @Override
    public void deleteEvent(Integer id) {
        eventRepositoryPort.delete(id);
    }

    @Override
    public Event updateEvent(Integer id, Event event) {
        validationService.validateNoDuplicate(event);
        return eventRepositoryPort.update(id, event);
    }


}
