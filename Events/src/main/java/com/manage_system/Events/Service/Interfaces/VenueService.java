package com.manage_system.Events.Service.Interfaces;

import com.manage_system.Events.DTO.VenueCreateDto;
import com.manage_system.Events.DTO.VenueResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService extends GenericService<VenueResponseDto,VenueCreateDto, Integer> {
    Page<VenueResponseDto> getFilteredVenues(
            String name,
            String location,
            Integer minCapacity,
            Pageable pageable
    );
}
