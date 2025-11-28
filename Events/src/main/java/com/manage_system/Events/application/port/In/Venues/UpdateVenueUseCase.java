package com.manage_system.Events.application.port.In.Venues;

import com.manage_system.Events.Domain.model.Venue;

public interface UpdateVenueUseCase {
    Venue updateVenue(Integer id, Venue venue);
}
