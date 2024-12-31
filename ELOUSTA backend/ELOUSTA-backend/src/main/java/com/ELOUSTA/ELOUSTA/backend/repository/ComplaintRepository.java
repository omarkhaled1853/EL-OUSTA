package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, String> {
    List<ComplaintEntity> findByState(String state);
    List<ComplaintEntity> findByUserId(Integer userId);
    List<ComplaintEntity> findByTechId(Integer techId);

}
