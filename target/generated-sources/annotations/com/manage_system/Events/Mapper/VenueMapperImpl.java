package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.VenueDto;
import com.manage_system.Events.Entity.VenueEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T06:42:17-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class VenueMapperImpl implements VenueMapper {

    @Override
    public VenueDto toDto(VenueEntity venue) {
        if ( venue == null ) {
            return null;
        }

        VenueDto venueDto = new VenueDto();

        venueDto.setName( venue.getName() );
        venueDto.setLocation( venue.getLocation() );
        venueDto.setCapacity( venue.getCapacity() );

        return venueDto;
    }

    @Override
    public VenueEntity toEntity(VenueDto venueDto) {
        if ( venueDto == null ) {
            return null;
        }

        VenueEntity venueEntity = new VenueEntity();

        venueEntity.setName( venueDto.getName() );
        venueEntity.setLocation( venueDto.getLocation() );
        venueEntity.setCapacity( venueDto.getCapacity() );

        return venueEntity;
    }

    @Override
    public void updateEntityFromDTO(VenueDto dto, VenueEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        entity.setLocation( dto.getLocation() );
        entity.setCapacity( dto.getCapacity() );
    }
}
