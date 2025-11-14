package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.EventDto;
import com.manage_system.Events.Entity.EventEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T06:42:17-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDto toDto(EventEntity event) {
        if ( event == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setId( event.getId() );
        eventDto.setTitle( event.getTitle() );
        eventDto.setDescription( event.getDescription() );
        eventDto.setStartAt( event.getStartAt() );
        eventDto.setEndAt( event.getEndAt() );
        eventDto.setVenueId( event.getVenueId() );

        return eventDto;
    }

    @Override
    public EventEntity toEntity(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();

        eventEntity.setId( eventDto.getId() );
        eventEntity.setTitle( eventDto.getTitle() );
        eventEntity.setDescription( eventDto.getDescription() );
        eventEntity.setStartAt( eventDto.getStartAt() );
        eventEntity.setEndAt( eventDto.getEndAt() );
        eventEntity.setVenueId( eventDto.getVenueId() );

        return eventEntity;
    }

    @Override
    public void updateEntityFromDTO(EventDto dto, EventEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
        entity.setDescription( dto.getDescription() );
        entity.setStartAt( dto.getStartAt() );
        entity.setEndAt( dto.getEndAt() );
        entity.setVenueId( dto.getVenueId() );
    }
}
