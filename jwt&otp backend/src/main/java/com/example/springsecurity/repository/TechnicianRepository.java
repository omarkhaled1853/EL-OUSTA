package com.example.springsecurity.repository;

import com.example.springsecurity.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Integer> {
    Optional<TechnicianEntity> findByUsername(String username);
    Optional<TechnicianEntity> findByEmailAddress(String emailAddress);
}
