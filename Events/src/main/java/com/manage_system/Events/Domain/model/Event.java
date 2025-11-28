package com.manage_system.Events.Domain.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record Event(
        int id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Integer venueId
) {
}
