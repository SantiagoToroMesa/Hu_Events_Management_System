package com.manage_system.Events.application.port.In.Venues;

import com.manage_system.Events.Domain.model.Venue;

public interface CreateVenueUseCase {
    Venue createVenue(Venue venue);
}
