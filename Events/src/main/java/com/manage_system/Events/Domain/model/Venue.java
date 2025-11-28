package com.manage_system.Events.Domain.model;

public record Venue(
        int venueId,
        String name,
        String location,
        int capacity
) {
}
