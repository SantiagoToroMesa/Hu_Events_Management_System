package com.manage_system.Events.Controller;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import com.manage_system.Events.Exception.ResourceNotFoundException;
import com.manage_system.Events.Service.Interfaces.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {
    @Autowired
    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @Operation(summary = "Get all venues", description = "Retrieve a list of all venues in the system")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<VenueResponseDto>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAll());
    }

    @Operation(summary = "Get venue by ID", description = "Retrieve a specific venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueResponseDto> getVenueById(@PathVariable int id) {
        return venueService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with the id: " + id));
    }

    @Operation(summary = "Create a new venue", description = "Add a new venue to the system")
    @ApiResponse(responseCode = "200", description = "Venue created successfully")
    @PostMapping
    public ResponseEntity<VenueResponseDto> createVenue(@RequestBody VenueCreateDto venueCreateDto) {
        VenueResponseDto createdVenue = venueService.create(venueCreateDto);
        return ResponseEntity.ok(createdVenue);
    }

    @Operation(summary = "Delete a venue", description = "Delete a venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable int id) {
        boolean deleted = venueService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a venue", description = "Update the details of an existing venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueResponseDto> updateVenue(@PathVariable int id, @RequestBody VenueCreateDto venueCreateDto) {
        VenueResponseDto updatedVenue = venueService.update(id, venueCreateDto);
        if (updatedVenue != null) {
            return ResponseEntity.ok(updatedVenue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<VenueResponseDto>>
    filterVenues(@RequestParam(required = false) String name,
                 @RequestParam(required = false) String location,
                 @RequestParam(required = false) Integer capacity,
                 Pageable pageable){
        return ResponseEntity.ok(venueService.getFilteredVenues(name, location, capacity, pageable));
    }

}
