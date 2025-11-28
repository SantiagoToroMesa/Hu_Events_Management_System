package com.manage_system.Events.Infrastucture.persistence.repository;

import com.manage_system.Events.Infrastucture.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataEventRepository extends JpaRepository<EventEntity, Integer> {
    boolean existsByTitle(String title);
}
