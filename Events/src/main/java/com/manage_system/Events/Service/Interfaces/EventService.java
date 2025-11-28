package com.manage_system.Events.Service.Interfaces;

import com.manage_system.Events.DTO.EventCreateDto;
import com.manage_system.Events.DTO.EventResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EventService extends GenericService<EventResponseDto, EventCreateDto, Integer> {
    Page<EventResponseDto> getFilteredEvents(
            String title,
            LocalDateTime startDate,
            Integer venueId,
            Pageable pageable
    );
}
