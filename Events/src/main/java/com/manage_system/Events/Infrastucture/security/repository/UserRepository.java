package com.manage_system.Events.Infrastucture.security.repository;

import com.manage_system.Events.Infrastucture.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
