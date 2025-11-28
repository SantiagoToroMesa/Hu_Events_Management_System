package com.manage_system.Events.Infrastucture.persistence.adapter;

import com.manage_system.Events.Domain.model.Venue;
import com.manage_system.Events.Infrastucture.mapper.VenueMapper;
import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import com.manage_system.Events.Infrastucture.persistence.entity.VenueEntity;
import com.manage_system.Events.Infrastucture.persistence.repository.SpringDataVenueRepository;
import com.manage_system.Events.application.port.Out.Venues.VenueRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaVenueRepositoryAdapter implements VenueRepositoryPort {
    private final SpringDataVenueRepository venueRepository;
    private final VenueMapper mapper;

    public JpaVenueRepositoryAdapter(SpringDataVenueRepository venueRepository, VenueMapper mapper) {
        this.venueRepository = venueRepository;
        this.mapper = mapper;
    }

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = mapper.createDomainToEntity(venue);
        VenueEntity venueSaved = venueRepository.save(entity);
        return mapper.createEntityToDomain(venueSaved);
    }

    @Override
    public Optional<Venue> getVenueById(Integer id) {
        return venueRepository.findById(id).map(mapper::createEntityToDomain);
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll().stream().map(mapper::createEntityToDomain).toList();
    }

    @Override
    public void delete(Integer id) {
        venueRepository.deleteById(id);
    }

    @Override
    public Venue update(Integer id, Venue venue) {
        return venueRepository.findById(id)
                .map(existingEntity -> {

                    mapper.updateEntityFromDomain(venue, existingEntity);

                    VenueEntity updated = venueRepository.save(existingEntity);

                    return mapper.createEntityToDomain(updated);
                })
                .orElse(null);
    }

}

