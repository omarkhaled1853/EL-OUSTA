package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Integer> {
}
