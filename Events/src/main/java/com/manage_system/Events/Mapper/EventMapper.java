package com.manage_system.Events.Mapper;
import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {VenueIdMapper.class})
public interface EventMapper {

    @Mapping(source = "venueId", target = "venue")
    EventEntity toEntity(EventCreateDto dto);

    @Mapping(source = "venue", target = "venueId")
    EventResponseDto toDto(EventEntity entity);

    void updateEntityFromDTO(EventCreateDto dto, @MappingTarget EventEntity entity);
}

