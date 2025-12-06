package com.manage_system.Events.Infrastucture.controller;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Infrastucture.mapper.EventMapper;
import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.EventResponseDto;
import com.manage_system.Events.application.port.In.Events.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventMapper mapper;
    private final CreateEventUseCase createEventUseCase;
    private final GetEventByIdUseCase getEventByIdUseCase;
    private final GetAllEventsUseCase getAllEventsUseCase;
    private final UpdateEventUseCase updateEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final FilterEventsUseCase filterEventsUseCase;

    public EventController(EventMapper mapper, CreateEventUseCase createEventUseCase, GetEventByIdUseCase getEventByIdUseCase, GetAllEventsUseCase getAllEventsUseCase, UpdateEventUseCase updateEventUseCase, DeleteEventUseCase deleteEventUseCase, FilterEventsUseCase filterEventsUseCase) {
        this.mapper = mapper;
        this.createEventUseCase = createEventUseCase;
        this.getEventByIdUseCase = getEventByIdUseCase;
        this.getAllEventsUseCase = getAllEventsUseCase;
        this.updateEventUseCase = updateEventUseCase;
        this.deleteEventUseCase = deleteEventUseCase;
        this.filterEventsUseCase = filterEventsUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new event", description = "Add a new event to the system")
    @ApiResponse(responseCode = "200", description = "Event created successfully")
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody @Valid EventCreateDto eventDto){
        log.info("Received request to create event: {}", eventDto.getTitle());

        Event event = mapper.createDtoToDomain(eventDto);

        Event eventCreated = createEventUseCase.createEvent(event);

        log.info("Event successfully created with ID: {}", eventCreated.title());
        return ResponseEntity.ok(mapper.createDomainToDto(eventCreated));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all events", description = "Retrieve a list of all events in the system")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents(){
        log.info("Received request to get all events.");
        List<Event> events = getAllEventsUseCase.getAllEvents();
        log.info("Retrieved {} events.", events.size());

        List<EventResponseDto> eventDtos = events.stream()
                .map(mapper::createDomainToDto)
                .toList();
        return ResponseEntity.ok(eventDtos);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @GetMapping("{id}")
    public ResponseEntity<EventResponseDto> getEventbyId(@PathVariable Integer id){
        log.info("Received request to get event by ID: {}", id);

        Event event = getEventByIdUseCase.getEventById(id);

        log.info("Event ID {} retrieved successfully.", id);
        return ResponseEntity.ok(mapper.createDomainToDto(event));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an event", description = "Delete an event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Integer id){
        log.warn("Received request to delete event by ID: {}", id);

        // Si no se encuentra, el UseCase lanza EntityNotFoundException (404)
        deleteEventUseCase.deleteEvent(id);

        log.info("Event ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an event", description = "Update an existing event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Integer id, @RequestBody @Valid EventCreateDto eventDto) {
        log.info("Received request to update event ID {}.", id);

        Event event = mapper.createDtoToDomain(eventDto);
        // Si no se encuentra, el UseCase lanza EntityNotFoundException (404)
        Event eventUpdated = updateEventUseCase.updateEvent(id, event);

        // Eliminado: El UseCase ahora debe lanzar 404, no devolver null.
        // if (eventUpdated == null) { return ResponseEntity.notFound().build(); }

        log.info("Event ID {} updated successfully.", id);
        return ResponseEntity.ok(mapper.createDomainToDto(eventUpdated));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDto>> filterEvents(
            @RequestParam(required = false) Integer venueId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        log.info("Received request to filter events.");

        List<Event> events = filterEventsUseCase.filterEvents(venueId, start, end);

        log.info("Filtered events returned {} results.", events.size());
        return ResponseEntity.ok(
                events.stream()
                        .map(mapper::createDomainToDto)
                        .toList()
        );
    }
}