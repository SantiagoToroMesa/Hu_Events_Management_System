package com.manage_system.Events.Infrastucture.persistence.repository;

import com.manage_system.Events.Infrastucture.persistence.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataVenueRepository extends JpaRepository<VenueEntity, Integer> {
}
