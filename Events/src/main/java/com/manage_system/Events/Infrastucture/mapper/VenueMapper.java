package com.manage_system.Events.Infrastucture.mapper;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Domain.model.Venue;
import com.manage_system.Events.Infrastucture.controller.dto.EventCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.EventResponseDto;
import com.manage_system.Events.Infrastucture.controller.dto.VenueCreateDto;
import com.manage_system.Events.Infrastucture.controller.dto.VenueResponseDto;
import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import com.manage_system.Events.Infrastucture.persistence.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    VenueResponseDto toDto(VenueEntity venue);

    VenueEntity toEntity(VenueCreateDto venueCreateDto);

    void updateEntityFromDTO(VenueCreateDto dto, @MappingTarget VenueEntity entity);

    @Mapping(target = "venueId", ignore = true)
    void updateEntityFromDomain(Venue domain, @MappingTarget VenueEntity entity);

    VenueEntity createDomainToEntity(Venue domain);

    Venue createEntityToDomain(VenueEntity entity);

    Venue createDtoToDomain(VenueCreateDto dto);

    VenueResponseDto createDomainToDto(Venue venue);

}
