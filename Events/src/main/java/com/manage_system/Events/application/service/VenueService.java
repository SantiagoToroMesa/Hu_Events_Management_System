package com.manage_system.Events.application.service;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.model.Venue;
import com.manage_system.Events.Infrastucture.mapper.VenueMapper;
import com.manage_system.Events.application.port.In.Events.GetAllEventsUseCase;
import com.manage_system.Events.application.port.In.Events.GetEventByIdUseCase;
import com.manage_system.Events.application.port.In.Venues.*;
import com.manage_system.Events.application.port.Out.Events.EventRepositoryPort;
import com.manage_system.Events.application.port.Out.Venues.VenueRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService implements CreateVenueUseCase, GetVenueByIdUseCase, GetAllVenuesUseCase, DeleteVenueUseCase, UpdateVenueUseCase {
    private final VenueRepositoryPort venueRepositoryPort;


    public VenueService(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }


    @Override
    public Venue createVenue(Venue venue) {
        return venueRepositoryPort.save(venue);
    }


    @Override
    public List<Venue> getAllVenues() {
        return venueRepositoryPort.getAllVenues();
    }

    @Override
    public Optional<Venue> getVenueById(Integer id) {
        return venueRepositoryPort.getVenueById(id);
    }

    @Override
    public void deleteVenue(Integer id) {
        venueRepositoryPort.delete(id);
    }

    @Override
    public Venue updateVenue(Integer id, Venue venue) {
        return venueRepositoryPort.update(id, venue);
    }
}
