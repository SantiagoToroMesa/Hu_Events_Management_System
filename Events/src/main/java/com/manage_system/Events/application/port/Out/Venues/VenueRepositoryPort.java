package com.manage_system.Events.application.port.Out.Venues;

import com.manage_system.Events.Domain.model.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> getVenueById(Integer id);
    List<Venue> getAllVenues();
    void delete(Integer id);
    Venue update(Integer id, Venue venue);
}
