package com.manage_system.Events.Repository.Memory;

import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Repository.Interfaces.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventMemoryRepository implements EventRepository {
    private final List<EventEntity> eventEntities = new ArrayList<>();

    @Override
    public EventEntity create(EventEntity entity) {
        eventEntities.add(entity);
        return entity;
    }

    @Override
    public List<EventEntity> getAll() {
        return eventEntities;
    }

    @Override
    public Optional<EventEntity> getById(Integer integer) {
        return eventEntities.stream().filter(v -> v.getId() == integer).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        return eventEntities.removeIf(v -> v.getId() == integer);
    }

    @Override
    public EventEntity update(Integer integer, EventEntity entity) {
        for(EventEntity e : eventEntities){
            if(e.getId() == integer){
                e.setTitle(entity.getTitle());
                e.setDescription(entity.getDescription());
                e.setStartAt(entity.getStartAt());
                e.setEndAt(entity.getEndAt());
                e.setVenue(entity.getVenue());
                return e;
            }
        }
        return null;
    }
}
