package com.manage_system.Events.application;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.service.EventValidationService;
import com.manage_system.Events.application.port.Out.Events.EventRepositoryPort;
import com.manage_system.Events.application.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepositoryPort repo;

    @Mock
    private EventValidationService validation;

    @InjectMocks
    private EventService service;

    private Event getSampleEvent() {
        return new Event(
                1,
                "Test Event",
                "Description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                1
        );
    }

    // ------------------------------
    // CREATE EVENT
    // ------------------------------
    @Test
    void shouldCreateEventSuccessfully() {
        Event event = getSampleEvent();

        doNothing().when(validation).validateNoDuplicate(event);
        when(repo.save(event)).thenReturn(event);

        Event result = service.createEvent(event);

        assertEquals(event, result);
        verify(repo).save(event);
        verify(validation).validateNoDuplicate(event);
    }

    // ------------------------------
    // GET EVENT BY ID
    // ------------------------------
    @Test
    void shouldReturnEventById() {
        Event event = getSampleEvent();

        when(repo.getEventById(1)).thenReturn(Optional.of(event));

        Event result = service.getEventById(1);

        assertEquals(event, result);
    }

    @Test
    void shouldThrowWhenEventNotFound() {
        when(repo.getEventById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getEventById(99));
    }

    // ------------------------------
    // GET ALL
    // ------------------------------
    @Test
    void shouldReturnAllEvents() {
        Event event = getSampleEvent();
        when(repo.getAllEvents()).thenReturn(List.of(event));

        List<Event> result = service.getAllEvents();

        assertEquals(1, result.size());
    }

    // ------------------------------
    // DELETE
    // ------------------------------
    @Test
    void shouldDeleteEventSuccessfully() {
        Event event = getSampleEvent();

        when(repo.getEventById(1)).thenReturn(Optional.of(event));

        service.deleteEvent(1);

        verify(repo).delete(1);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingEvent() {
        when(repo.getEventById(5)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteEvent(5));
    }

    // ------------------------------
    // UPDATE
    // ------------------------------
    @Test
    void shouldUpdateEventSuccessfully() {
        Event event = getSampleEvent();

        when(repo.getEventById(1)).thenReturn(Optional.of(event));
        doNothing().when(validation).validateNoDuplicate(event);
        when(repo.update(1, event)).thenReturn(event);

        Event result = service.updateEvent(1, event);

        assertEquals(event, result);
        verify(repo).update(1, event);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingEvent() {
        Event event = getSampleEvent();

        when(repo.getEventById(100)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.updateEvent(100, event));
    }

    // ------------------------------
    // FILTER
    // ------------------------------
    @Test
    void shouldFilterEventsCorrectly() {
        Event event = getSampleEvent();

        when(repo.filterEvents(1, null, null)).thenReturn(List.of(event));

        List<Event> result = service.filterEvents(1, null, null);

        assertEquals(1, result.size());
        verify(repo).filterEvents(1, null, null);
    }
}
