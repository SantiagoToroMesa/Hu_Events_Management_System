package com.manage_system.Events.Repository.Impl;

import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Entity.VenueEntity;
import com.manage_system.Events.Repository.Interfaces.VenueRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VenueMemoryRepository implements VenueRepository {
    private final List<VenueEntity> venueEntities = new ArrayList<>();


    @Override
    public VenueEntity create(VenueEntity entity) {
        venueEntities.add(entity);
        return null;
    }

    @Override
    public List<VenueEntity> getAll() {
        return venueEntities;
    }

    @Override
    public Optional<VenueEntity> getById(Integer integer) {
        return venueEntities.stream().filter(venue -> venue.getVenueId() == integer).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        return venueEntities.removeIf(venue -> venue.getVenueId() == integer);
    }

    @Override
    public VenueEntity update(Integer integer, VenueEntity entity) {
        for(VenueEntity e : venueEntities){
            if(e.getVenueId() == integer){
                e.setName(entity.getName());
                e.setLocation(entity.getLocation());
                e.setCapacity(entity.getCapacity());
                return e;
            }
        }
        return null;
    }
}
