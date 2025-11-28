package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import com.manage_system.Events.Entity.VenueEntity;
import com.manage_system.Events.Mapper.VenueMapper;
import com.manage_system.Events.Repository.JPA.VenueJPARepository;
import com.manage_system.Events.Repository.Interfaces.VenueRepository;
import com.manage_system.Events.Service.Interfaces.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueJPARepository venueRepository;
    private final VenueMapper mapper;
//    int idCounter = 1;

    public VenueServiceImpl(VenueJPARepository venueRepository, VenueMapper venueMapper) {
        this.venueRepository = venueRepository;
        this.mapper = venueMapper;
    }

    @Override
    public VenueResponseDto create(VenueCreateDto entity) {
        VenueEntity venue = mapper.toEntity(entity);
        venueRepository.save(venue);
        return mapper.toDto(venue);
    }

    @Override
    public List<VenueResponseDto> getAll() {
        return venueRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<VenueResponseDto> getById(Integer integer) {
        return venueRepository.findAll().stream().filter
                (v -> v.getVenueId() == integer).map(mapper::toDto).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        VenueEntity venue = venueRepository.findById(integer).orElse(null);
        if(venue == null){
            return false;
        }
        venueRepository.delete(venue);
        return true;
    }

    @Override
    public VenueResponseDto update(Integer integer, VenueCreateDto entity) {
        Optional<VenueEntity> optional = venueRepository.findById(integer);
        if(optional.isEmpty()){
            return null;
        }
        boolean duplicated = venueRepository.findAll().stream().
                anyMatch(Venue -> Venue.getVenueId() != integer
                        && Venue.getName().equalsIgnoreCase(entity.getName()));

        if(duplicated){
            throw new RuntimeException("Venue name already exists");
        }

        VenueEntity existing = optional.get();
        mapper.updateEntityFromDTO(entity, existing);
        VenueEntity update = venueRepository.save(existing);
        return mapper.toDto(update);
    }

    @Override
    public Page<VenueResponseDto> getFilteredVenues(String name, String location, Integer minCapacity, Pageable pageable) {
        Page<VenueEntity> result = venueRepository.getFilteredVenues(name,location,minCapacity,pageable);
        return result.map(mapper::toDto);
    }
}
