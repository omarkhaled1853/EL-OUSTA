package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
    Optional<Technician> findByUsername(String username);
    Optional<Technician> findByEmailAddress(String emailAddress);
}
