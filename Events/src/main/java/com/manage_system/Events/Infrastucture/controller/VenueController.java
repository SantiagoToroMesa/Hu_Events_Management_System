package com.manage_system.Events.Infrastucture.controller;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.model.Venue;
import com.manage_system.Events.Infrastucture.controller.dto.VenueCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.VenueResponseDto;
import com.manage_system.Events.Infrastucture.mapper.VenueMapper;
import com.manage_system.Events.application.port.In.Venues.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueMapper mapper;
    private final CreateVenueUseCase createVenueUseCase;
    private final GetVenueByIdUseCase getVenueByIdUseCase;
    private final GetAllVenuesUseCase getAllVenuesUseCase;
    private final DeleteVenueUseCase deleteVenueUseCase;
    private final UpdateVenueUseCase updateVenueUseCase;

    public VenueController(VenueMapper mapper, CreateVenueUseCase createVenueUseCase, GetVenueByIdUseCase getVenueByIdUseCase, GetAllVenuesUseCase getAllVenuesUseCase, DeleteVenueUseCase deleteVenueUseCase, UpdateVenueUseCase updateVenueUseCase) {
        this.mapper = mapper;
        this.createVenueUseCase = createVenueUseCase;
        this.getVenueByIdUseCase = getVenueByIdUseCase;
        this.getAllVenuesUseCase = getAllVenuesUseCase;
        this.deleteVenueUseCase = deleteVenueUseCase;
        this.updateVenueUseCase = updateVenueUseCase;
    }

    @PostMapping
    public ResponseEntity<VenueResponseDto> createVenue(@RequestBody VenueCreateDto venueDto){
        Venue venue = mapper.createDtoToDomain(venueDto);
        Venue venueCreated = createVenueUseCase.createVenue(venue);
        return ResponseEntity.ok(mapper.createDomainToDto(venueCreated));
    }

    @Operation(summary = "Get all venues", description = "Retrieve a list of all venues in the system")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<List<VenueResponseDto>> getAllVenues(){
        List<Venue> venues = getAllVenuesUseCase.getAllVenues();
        List<VenueResponseDto> venueDtos = venues.stream()
                .map(mapper::createDomainToDto)
                .toList();
        return ResponseEntity.ok(venueDtos);
    }

    @Operation(summary = "Get venue by ID", description = "Retrieve a specific venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied")
    })
    @GetMapping("{id}")
    public ResponseEntity<VenueResponseDto> getVenueByid(@PathVariable int id){
        Optional<Venue> venueOpt = getVenueByIdUseCase.getVenueById(id);
        return venueOpt.map(venue -> ResponseEntity.ok(mapper.createDomainToDto(venue))).
                orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a venue", description = "Delete a venue by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable int id) {
        deleteVenueUseCase.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a venue", description = "Update the details of an existing venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @PutMapping("{id}")
    public ResponseEntity<VenueResponseDto> updateVenue (@PathVariable int id, @RequestBody VenueCreateDto venueDto){
        Venue venue = mapper.createDtoToDomain(venueDto);
        Venue venueUpdated = updateVenueUseCase.updateVenue(id, venue);
        if (venueUpdated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.createDomainToDto(venueUpdated));
    }

}
