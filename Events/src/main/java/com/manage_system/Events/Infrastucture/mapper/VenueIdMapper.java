package com.manage_system.Events.Infrastucture.mapper;

import com.manage_system.Events.Infrastucture.persistence.entity.VenueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueIdMapper {

    default Integer map(VenueEntity venue) {
        return venue != null ? venue.getVenueId() : null;
    }

    default VenueEntity map(Integer venueId) {
        if (venueId == null) return null;

        VenueEntity venue = new VenueEntity();
        venue.setVenueId(venueId);
        return venue;
    }
}
