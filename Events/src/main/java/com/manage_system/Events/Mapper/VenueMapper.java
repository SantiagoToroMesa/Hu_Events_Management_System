package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.VenueDto;
import com.manage_system.Events.Entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueDto toDto(VenueEntity venue);
    VenueEntity toEntity(VenueDto venueDto);

    void updateEntityFromDTO(VenueDto dto, @MappingTarget VenueEntity entity);
}
