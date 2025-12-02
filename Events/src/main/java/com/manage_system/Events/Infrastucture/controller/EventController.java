package com.manage_system.Events.Infrastucture.controller;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Infrastucture.mapper.EventMapper;
import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.EventResponseDto;
import com.manage_system.Events.application.port.In.Events.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "Create a new event", description = "Add a new event to the system")
    @ApiResponse(responseCode = "200", description = "Event created successfully")
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventCreateDto eventDto){
        Event event = mapper.createDtoToDomain(eventDto);
        Event eventCreated = createEventUseCase.createEvent(event);
        return ResponseEntity.ok(mapper.createDomainToDto(eventCreated));
    }

    @Operation(summary = "Get all events", description = "Retrieve a list of all events in the system")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents(){
        List<Event> events = getAllEventsUseCase.getAllEvents();
        List<EventResponseDto> eventDtos = events.stream()
                .map(mapper::createDomainToDto)
                .toList();
        return ResponseEntity.ok(eventDtos);
    }

    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @GetMapping("{id}")
    public ResponseEntity<EventResponseDto> getEventbyId(@PathVariable Integer id){
        Optional<Event> eventOpt = getEventByIdUseCase.getEventById(id);
        return eventOpt
                .map(event -> ResponseEntity.ok(mapper.createDomainToDto(event)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an event", description = "Delete an event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Integer id){
        deleteEventUseCase.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an event", description = "Update an existing event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Integer id, @RequestBody EventCreateDto eventDto) {
        Event event = mapper.createDtoToDomain(eventDto);
        Event eventUpdated = updateEventUseCase.updateEvent(id, event);
        if (eventUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.createDomainToDto(eventUpdated));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDto>> filterEvents(
            @RequestParam(required = false) Integer venueId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<Event> events = filterEventsUseCase.filterEvents(venueId, start, end);

        return ResponseEntity.ok(
                events.stream()
                        .map(mapper::createDomainToDto)
                        .toList()
        );
    }

}
