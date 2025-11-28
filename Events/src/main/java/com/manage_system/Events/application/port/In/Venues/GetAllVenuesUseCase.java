package com.manage_system.Events.application.port.In.Venues;

import com.manage_system.Events.Domain.model.Venue;

import java.util.List;

public interface GetAllVenuesUseCase {
    List<Venue> getAllVenues();
}
