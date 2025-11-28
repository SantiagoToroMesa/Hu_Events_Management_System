package com.manage_system.Events.application.port.In.Venues;

import com.manage_system.Events.Domain.model.Venue;

import java.util.Optional;

public interface GetVenueByIdUseCase {
    Optional<Venue> getVenueById(Integer id);
}
