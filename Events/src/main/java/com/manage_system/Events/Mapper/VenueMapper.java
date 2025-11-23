package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import com.manage_system.Events.Entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    VenueResponseDto toDto(VenueEntity venue);

    VenueEntity toEntity(VenueCreateDto venueCreateDto);

    void updateEntityFromDTO(VenueCreateDto dto, @MappingTarget VenueEntity entity);
}
