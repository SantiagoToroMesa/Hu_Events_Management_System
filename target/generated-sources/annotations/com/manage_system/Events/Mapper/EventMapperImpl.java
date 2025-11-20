package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-19T12:09:00-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventResponseDto toDto(EventEntity event) {
        if ( event == null ) {
            return null;
        }

        EventResponseDto eventResponseDto = new EventResponseDto();

        eventResponseDto.setTitle( event.getTitle() );
        eventResponseDto.setId( event.getId() );
        eventResponseDto.setDescription( event.getDescription() );
        eventResponseDto.setStartAt( event.getStartAt() );
        eventResponseDto.setEndAt( event.getEndAt() );
        eventResponseDto.setVenueId( event.getVenueId() );

        return eventResponseDto;
    }

    @Override
    public EventEntity toEntity(EventCreateDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();

        eventEntity.setTitle( eventDto.getTitle() );
        eventEntity.setDescription( eventDto.getDescription() );
        eventEntity.setStartAt( eventDto.getStartAt() );
        eventEntity.setEndAt( eventDto.getEndAt() );
        eventEntity.setVenueId( eventDto.getVenueId() );

        return eventEntity;
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
        entity.setVenueId( dto.getVenueId() );
    }
}
