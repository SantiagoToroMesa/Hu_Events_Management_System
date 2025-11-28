package com.manage_system.Events.Infrastucture.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venues")
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int venueId;
    private String name;
    private String location;
    private int capacity;
}
