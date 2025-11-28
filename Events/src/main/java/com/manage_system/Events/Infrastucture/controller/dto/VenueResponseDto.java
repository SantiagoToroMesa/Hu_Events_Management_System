package com.manage_system.Events.Infrastucture.controller.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
<<<<<<< HEAD:Events/src/main/java/com/manage_system/Events/Infrastucture/controller/dto/VenueResponseDto.java
@AllArgsConstructor
public class VenueResponseDto {
=======
@Table(name = "venues")
public class VenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> f84e8bf7c728d238955d4953dcc7af0f8f2729d8:Events/src/main/java/com/manage_system/Events/Entity/VenueEntity.java
    private int venueId;
    private String name;
    private String location;
    private int capacity;
}
