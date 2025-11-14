package com.manage_system.Events.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueEntity {
    private int venueId;
    private String name;
    private String location;
    private int capacity;
}
