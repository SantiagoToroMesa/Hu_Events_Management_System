package com.manage_system.Events.Controller;

import com.manage_system.Events.DTO.EventDto;
import com.manage_system.Events.DTO.VenueDto;
import com.manage_system.Events.Exception.ResourceNotFoundException;
import com.manage_system.Events.Service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<VenueDto>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAll());
    }

    @Operation(summary = "Get venue by ID", description = "Retrieve a specific venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getVenueById(@PathVariable int id) {
        return venueService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with the id: " + id));
    }

    @Operation(summary = "Create a new venue", description = "Add a new venue to the system")
    @ApiResponse(responseCode = "200", description = "Venue created successfully")
    @PostMapping
    public ResponseEntity<VenueDto> createVenue(VenueDto venueDto) {
        VenueDto createdVenue = venueService.create(venueDto);
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
    public ResponseEntity<VenueDto> updateVenue(@PathVariable int id, @RequestBody VenueDto venueDto) {
        VenueDto updatedVenue = venueService.update(id, venueDto);
        if (updatedVenue != null) {
            return ResponseEntity.ok(updatedVenue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
