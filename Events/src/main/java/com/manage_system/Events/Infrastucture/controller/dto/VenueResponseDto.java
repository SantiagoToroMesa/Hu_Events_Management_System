package com.manage_system.Events.Infrastucture.controller.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponseDto {
    private int venueId;
    private String name;
    private String location;
    private int capacity;
}
