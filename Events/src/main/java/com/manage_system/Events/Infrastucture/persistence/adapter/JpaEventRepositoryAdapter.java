package com.manage_system.Events.Infrastucture.persistence.adapter;

import com.manage_system.Events.Domain.model.Event;
import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import com.manage_system.Events.Infrastucture.persistence.repository.SpringDataEventRepository;
import com.manage_system.Events.Infrastucture.mapper.EventMapper;
import com.manage_system.Events.Infrastucture.persistence.spec.EventSpecifications;
import com.manage_system.Events.application.port.Out.Events.EventRepositoryPort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaEventRepositoryAdapter implements EventRepositoryPort {

    private final SpringDataEventRepository eventRepository;
    private final EventMapper mapper;

    public JpaEventRepositoryAdapter(SpringDataEventRepository eventRepository, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.createDomainToEntity(event);
        EventEntity eventsaved = eventRepository.save(entity);
        return mapper.createEntityToDomain(eventsaved);
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id).map(mapper::createEntityToDomain);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream().map(mapper::createEntityToDomain).toList();
    }

    @Override
    public void delete(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event update(Integer id, Event event) {

        return eventRepository.findById(id)
                .map(existingEntity -> {

                    mapper.updateEntityFromDomain(event, existingEntity);

                    EventEntity updated = eventRepository.save(existingEntity);

                    return mapper.createEntityToDomain(updated);
                })
                .orElse(null);
    }

    @Override
    public boolean existsByTitle(String title) {
        return eventRepository.existsByTitle(title);
    }

    @Override
    public List<Event> filterEvents(Integer venueId, LocalDateTime start, LocalDateTime end) {
        Specification<EventEntity> spec = Specification.allOf();

        if (venueId != null) {
            spec = spec.and(EventSpecifications.hasVenue(venueId));
        }
        if (start != null && end != null) {
            spec = spec.and(EventSpecifications.betweenDates(start, end));
        }
        List<EventEntity> entities = eventRepository.findAll(spec);

        return entities.stream()
                .map(mapper::createEntityToDomain)
                .toList();
    }

}
