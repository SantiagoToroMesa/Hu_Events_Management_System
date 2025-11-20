package com.manage_system.Events.Mapper;
import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(source = "title", target = "title")
    EventResponseDto toDto(EventEntity event);
    @Mapping(source = "title", target = "title")
    EventEntity toEntity(EventCreateDto eventDto);

    void updateEntityFromDTO(EventCreateDto dto, @MappingTarget EventEntity entity);
}
