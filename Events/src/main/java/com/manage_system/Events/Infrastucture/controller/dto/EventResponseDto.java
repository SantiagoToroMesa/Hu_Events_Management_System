package com.manage_system.Events.Infrastucture.controller.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
<<<<<<< HEAD:Events/src/main/java/com/manage_system/Events/Infrastucture/controller/dto/EventResponseDto.java
@NoArgsConstructor
public class EventResponseDto {
=======
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> f84e8bf7c728d238955d4953dcc7af0f8f2729d8:Events/src/main/java/com/manage_system/Events/Entity/EventEntity.java
    private int id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
<<<<<<< HEAD:Events/src/main/java/com/manage_system/Events/Infrastucture/controller/dto/EventResponseDto.java
    private Integer venueId;
=======
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private VenueEntity  venue;
>>>>>>> f84e8bf7c728d238955d4953dcc7af0f8f2729d8:Events/src/main/java/com/manage_system/Events/Entity/EventEntity.java
}
