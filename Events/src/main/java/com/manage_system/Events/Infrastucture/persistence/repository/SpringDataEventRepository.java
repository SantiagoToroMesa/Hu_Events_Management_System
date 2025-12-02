package com.manage_system.Events.Infrastucture.persistence.repository;

import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataEventRepository
        extends JpaRepository<EventEntity, Integer>, JpaSpecificationExecutor<EventEntity> {

    boolean existsByTitle(String title);

    @EntityGraph(attributePaths = {"venue"})
    List<EventEntity> findAll();

    List<EventEntity> findByVenue_VenueId(int venueId);

    List<EventEntity> findByStartAtBetween(LocalDateTime start, LocalDateTime end);
}
