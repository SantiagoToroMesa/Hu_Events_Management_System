package com.manage_system.Events.Service.Impl;

import com.manage_system.Events.DTO.VenueDto;
import com.manage_system.Events.Entity.EventEntity;
import com.manage_system.Events.Entity.VenueEntity;
import com.manage_system.Events.Mapper.VenueMapper;
import com.manage_system.Events.Service.VenueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {
    private final List<VenueEntity> venues = new ArrayList<>();
    private final VenueMapper mapper;
    int idCounter = 1;

    public VenueServiceImpl(VenueMapper venueMapper) {
        this.mapper = venueMapper;
    }

    @Override
    public VenueDto create(VenueDto entity) {
        entity.setId(idCounter++);
        VenueEntity event = mapper.toEntity(entity);
        venues.add(event);
        return mapper.toDto(event);
    }

    @Override
    public List<VenueDto> getAll() {
        return venues.stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<VenueDto> getById(Integer integer) {
        return venues.stream().filter(v -> v.getVenueId() == integer).map(mapper::toDto).findFirst();
    }

    @Override
    public boolean delete(Integer integer) {
        return venues.removeIf(v -> v.getVenueId() == integer);
    }

    @Override
    public VenueDto update(Integer integer, VenueDto entity) {
        for(VenueEntity v : venues){
            if(v.getVenueId() == integer){
                mapper.updateEntityFromDTO(entity, v);
                return mapper.toDto(v);
            }
        }
        return null;
    }
}
