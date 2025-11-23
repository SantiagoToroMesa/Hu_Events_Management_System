package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import com.manage_system.Events.Entity.VenueEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T16:45:32-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class VenueMapperImpl implements VenueMapper {

    @Override
    public VenueResponseDto toDto(VenueEntity venue) {
        if ( venue == null ) {
            return null;
        }

        VenueResponseDto venueResponseDto = new VenueResponseDto();

        venueResponseDto.setVenueId( venue.getVenueId() );
        venueResponseDto.setName( venue.getName() );
        venueResponseDto.setLocation( venue.getLocation() );
        venueResponseDto.setCapacity( venue.getCapacity() );

        return venueResponseDto;
    }

    @Override
    public VenueEntity toEntity(VenueCreateDto venueCreateDto) {
        if ( venueCreateDto == null ) {
            return null;
        }

        VenueEntity venueEntity = new VenueEntity();

        venueEntity.setName( venueCreateDto.getName() );
        venueEntity.setLocation( venueCreateDto.getLocation() );
        venueEntity.setCapacity( venueCreateDto.getCapacity() );

        return venueEntity;
    }

    @Override
    public void updateEntityFromDTO(VenueCreateDto dto, VenueEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        entity.setLocation( dto.getLocation() );
        entity.setCapacity( dto.getCapacity() );
    }
}
