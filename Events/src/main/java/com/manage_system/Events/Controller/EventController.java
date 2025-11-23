package com.manage_system.Events.Controller;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Exception.ResourceNotFoundException;
import com.manage_system.Events.Service.Interfaces.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Operation(summary = "Get all events", description = "Retrieve a list of all events in the system")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @Operation(summary = "Create a new event", description = "Add a new event to the system")
    @ApiResponse(responseCode = "200", description = "Event created successfully")
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventCreateDto eventDto) {
        EventResponseDto createdEvent = eventService.create(eventDto);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable int id) {
        return eventService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Event not find with the id: " + id));
    }

    @Operation(summary = "Delete an event", description = "Delete an event by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id){
        boolean deleted = eventService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update an event", description = "Update an existing event by its ID")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Event updated successfully"),
    @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable int id, @RequestBody EventCreateDto eventDto) {
        EventResponseDto updatedEvent = eventService.update(id, eventDto);
        if (updatedEvent != null) {
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get events by filter", description = "Retrieve a list of events filtered by city, category and date")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")
            ,@ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/filter")
    public ResponseEntity<Page<EventResponseDto>> filterEvents(
            @RequestParam(required = false) String title,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam(required = false) Integer venueId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                eventService.getFilteredEvents(title, startDate, venueId, pageable)
        );
    }
}
