package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Mapper.EventMapper;
import com.manage_system.Events.Repository.JPA.EventJPARepository;
import com.manage_system.Events.Service.Interfaces.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventJPARepository eventRepository;
    private final EventMapper mapper;
//    int idCounter = 1;

    public EventServiceImpl(EventJPARepository eventRepository, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public EventResponseDto create(EventCreateDto entity) {

        boolean exists = eventRepository.findAll().stream()
                .anyMatch(e -> e.getTitle().equalsIgnoreCase(entity.getTitle()));

        if (exists) {
            throw new RuntimeException("409 - Event name already exists");
        }

        EventEntity event = mapper.toEntity(entity);
        eventRepository.save(event);

        return mapper.toDto(event);
    }


    @Override
    public List<EventResponseDto> getAll() {
        return eventRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<EventResponseDto> getById(Integer integer) {
        return eventRepository.findById(integer).map(mapper::toDto);
    }

    @Override
    public boolean delete(Integer integer) {
        EventEntity event = eventRepository.findById(integer).orElse(null);
        if(event == null){
            return false;
        }
        eventRepository.delete(event);
        return true;
    }

    @Override
    public EventResponseDto update(Integer integer, EventCreateDto entity) {
        Optional<EventEntity> optional = eventRepository.findById(integer);
        if(optional.isEmpty()){
            return null;
        }

        boolean duplicated = eventRepository.findAll().stream().
                anyMatch(event -> event.getId() != integer
                && event.getTitle().equalsIgnoreCase(entity.getTitle()));

        if(duplicated){
            throw new RuntimeException("Event title already exists");
        }
        EventEntity existing = optional.get();
        mapper.updateEntityFromDTO(entity, existing);
        EventEntity update = eventRepository.save(existing);
        return mapper.toDto(update);
    }


    @Override
    public Page<EventResponseDto> getFilteredEvents(String title, LocalDateTime startDate, Integer venueId, Pageable pageable) {
        Page<EventEntity> result = eventRepository.getFilteredEvents(title,startDate,venueId,pageable);
        return result.map(mapper::toDto);
    }
}
