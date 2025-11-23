package com.manage_system.Events.Mapper;

import com.manage_system.Events.Entity.VenueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueIdMapper {

    default int map(VenueEntity venue) {
        return venue != null ? venue.getVenueId() : 0;
    }

    default VenueEntity map(int venueId) {
        VenueEntity venue = new VenueEntity();
        venue.setVenueId(venueId);
        return venue;
    }
}

