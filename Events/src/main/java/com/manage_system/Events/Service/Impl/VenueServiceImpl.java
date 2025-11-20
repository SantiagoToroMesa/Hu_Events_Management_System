package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import com.manage_system.Events.Entity.VenueEntity;
import com.manage_system.Events.Mapper.VenueMapper;
import com.manage_system.Events.Repository.Interfaces.VenueRepository;
import com.manage_system.Events.Service.Interfaces.VenueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final VenueMapper mapper;
    int idCounter = 1;

    public VenueServiceImpl(VenueRepository venueRepository, VenueMapper venueMapper) {
        this.venueRepository = venueRepository;
        this.mapper = venueMapper;
    }

    @Override
    public VenueResponseDto create(VenueCreateDto entity) {
        VenueEntity venue = mapper.toEntity(entity);
        venue.setVenueId(idCounter++);
        venueRepository.create(venue);
        return mapper.toDto(venue);
    }

    @Override
    public List<VenueResponseDto> getAll() {
        return venueRepository.getAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<VenueResponseDto> getById(Integer integer) {
        return venueRepository.getAll().stream().filter
                (v -> v.getVenueId() == integer).map(mapper::toDto).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        return venueRepository.getAll().removeIf(v -> v.getVenueId() == integer);
    }

    @Override
    public VenueResponseDto update(Integer integer, VenueCreateDto entity) {
        Optional<VenueEntity> optional = venueRepository.getById(integer);
        if(optional.isEmpty()){
            return null;
        }
        boolean duplicated = venueRepository.getAll().stream().
                anyMatch(Venue -> Venue.getVenueId() == integer
                        && Venue.getName().equalsIgnoreCase(entity.getName()));

        if(duplicated){
            throw new RuntimeException("Event title already exists");
        }

        VenueEntity newData = mapper.toEntity(entity);
        VenueEntity update = venueRepository.update(integer, newData);
        if(update != null){
            return mapper.toDto(update);
        }
        return null;

    }
}
