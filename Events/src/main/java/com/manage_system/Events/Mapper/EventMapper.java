package com.manage_system.Events.Mapper;

import com.manage_system.Events.DTO.EventDto;
import com.manage_system.Events.Entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(EventEntity event);
    EventEntity toEntity(EventDto eventDto);

    void updateEntityFromDTO(EventDto dto, @MappingTarget EventEntity entity);
}
