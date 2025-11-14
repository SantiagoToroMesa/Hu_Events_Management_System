package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.EventDto;
import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Mapper.EventMapper;
import com.manage_system.Events.Service.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final List<EventEntity> events = new ArrayList<>();
    private final EventMapper mapper;
    int idCounter = 1;

    public EventServiceImpl(EventMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public EventDto create(EventDto entity) {
        entity.setId(idCounter++);
        EventEntity event = mapper.toEntity(entity);
        events.add(event);
        return mapper.toDto(event);
    }

    @Override
    public List<EventDto> getAll() {
        return events.stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<EventDto> getById(Integer integer) {
        return events.stream().filter(v -> v.getId() == integer).map(mapper::toDto).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        return events.removeIf(v -> v.getId() == integer);
    }

    @Override
    public EventDto update(Integer integer, EventDto entity) {
        for(EventEntity event : events){
            if(event.getId() == integer){
                mapper.updateEntityFromDTO(entity, event);
                return mapper.toDto(event);
            }
        }
        return null;
    }
}
