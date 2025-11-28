package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T16:45:32-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Autowired
    private VenueIdMapper venueIdMapper;

    @Override
    public EventEntity toEntity(EventCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();

        if ( dto.getVenueId() != null ) {
            eventEntity.setVenue( venueIdMapper.map( dto.getVenueId().intValue() ) );
        }
        eventEntity.setTitle( dto.getTitle() );
        eventEntity.setDescription( dto.getDescription() );
        eventEntity.setStartAt( dto.getStartAt() );
        eventEntity.setEndAt( dto.getEndAt() );

        return eventEntity;
    }

    @Override
    public EventResponseDto toDto(EventEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EventResponseDto eventResponseDto = new EventResponseDto();

        eventResponseDto.setVenueId( venueIdMapper.map( entity.getVenue() ) );
        eventResponseDto.setId( entity.getId() );
        eventResponseDto.setTitle( entity.getTitle() );
        eventResponseDto.setDescription( entity.getDescription() );
        eventResponseDto.setStartAt( entity.getStartAt() );
        eventResponseDto.setEndAt( entity.getEndAt() );

        return eventResponseDto;
    }

    @Override
    public void updateEntityFromDTO(EventCreateDto dto, EventEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setTitle( dto.getTitle() );
        entity.setDescription( dto.getDescription() );
        entity.setStartAt( dto.getStartAt() );
        entity.setEndAt( dto.getEndAt() );
    }
}
