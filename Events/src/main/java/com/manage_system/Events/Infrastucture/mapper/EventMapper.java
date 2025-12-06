package com.manage_system.Events.Infrastucture.mapper;

import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.EventResponseDto;
import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = { VenueIdMapper.class }
)
public interface EventMapper {

    @Mapping(source = "venueId", target = "venue")
    EventEntity toEntity(EventCreateDto dto);

    @Mapping(source = "venue", target = "venueId")
    EventResponseDto toDto(EventEntity entity);

    @Mapping(source = "venueId", target = "venue")
    EventEntity createDomainToEntity(Event domain);

    @Mapping(source = "venue", target = "venueId")
    Event createEntityToDomain(EventEntity entity);

    Event createDtoToDomain(EventCreateDto dto);

    EventResponseDto createDomainToDto(Event event);

    @Mapping(source = "venueId", target = "venue")
    void updateEntityFromDTO(EventCreateDto dto, @MappingTarget EventEntity entity);
    void updateDomainFromEntity(EventEntity entity, @MappingTarget Event domain);
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDomain(Event event, @MappingTarget EventEntity entity);



}
