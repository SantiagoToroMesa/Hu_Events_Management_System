package com.manage_system.Events.Repository.JPA;

import com.manage_system.Events.Entity.EventEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Profile("dev")
@Repository
public interface EventJPARepository extends JpaRepository<EventEntity, Integer>{
    @Query("""
    SELECT e FROM EventEntity e
    WHERE (:title IS NULL OR e.title LIKE %:title%)
    AND (:startDate IS NULL OR e.startAt >= :startDate)
    AND (:venueId IS NULL OR e.venue.venueId = :venueId)
    """)
    Page<EventEntity> getFilteredEvents(
            @Param("title") String title,
            @Param("startDate") LocalDateTime startDate,
            @Param("venueId") Integer venueId,
            Pageable pageable
    );

}
