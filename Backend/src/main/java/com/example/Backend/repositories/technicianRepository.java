package com.example.Backend.repositories;

import com.example.Backend.entities.technicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface technicianRepository extends JpaRepository<technicianEntity,Integer> {
}
