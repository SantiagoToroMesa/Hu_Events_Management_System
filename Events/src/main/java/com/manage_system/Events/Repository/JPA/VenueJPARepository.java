package com.manage_system.Events.Repository.JPA;

import com.manage_system.Events.Entity.VenueEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Profile("dev")
@Repository
public interface VenueJPARepository extends JpaRepository<VenueEntity, Integer> {

    @Query("""
        SELECT v FROM VenueEntity v
        WHERE (:name IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:location IS NULL OR LOWER(v.location) LIKE LOWER(CONCAT('%', :location, '%')))
        AND (:capacity IS NULL OR v.capacity >= :capacity)
    """)
    Page<VenueEntity> getFilteredVenues(
            @Param("name") String name,
            @Param("location") String location,
            @Param("capacity") Integer capacity,
            Pageable pageable
    );

}
