package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Mapper.EventMapper;
import com.manage_system.Events.Repository.Interfaces.EventRepository;
import com.manage_system.Events.Service.Interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper mapper;
    int idCounter = 1;

    public EventServiceImpl(EventRepository eventRepository, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public EventResponseDto create(EventCreateDto entity) {
        boolean exists = eventRepository.getAll().stream()
                .anyMatch(e -> e.getTitle().equalsIgnoreCase(entity.getTitle()));

        if (exists) {
            throw new RuntimeException("409 - Event name already exists");
        }

        EventEntity event = mapper.toEntity(entity);
        event.setId(idCounter++);
        eventRepository.create(event);
        return mapper.toDto(event);
    }

    @Override
    public List<EventResponseDto> getAll() {
        return eventRepository.getAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<EventResponseDto> getById(Integer integer) {
        return eventRepository.getById(integer).map(mapper::toDto);
    }

    @Override
    public boolean delete(Integer integer) {
        return eventRepository.delete(integer);
    }

    @Override
    public EventResponseDto update(Integer integer, EventCreateDto entity) {
        Optional<EventEntity> optional = eventRepository.getById(integer);
        if(optional.isEmpty()){
            return null;
        }

        boolean duplicated = eventRepository.getAll().stream().
                anyMatch(event -> event.getId() == integer
                && event.getTitle().equalsIgnoreCase(entity.getTitle()));

        if(duplicated){
            throw new RuntimeException("Event title already exists");
        }
        EventEntity newData = mapper.toEntity(entity);
        EventEntity update = eventRepository.update(integer, newData);
        return mapper.toDto(update);
    }
}
