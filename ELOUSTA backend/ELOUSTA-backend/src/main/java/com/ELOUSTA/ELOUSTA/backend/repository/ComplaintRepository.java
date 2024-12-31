package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ComplaintRepository extends JpaRepository<ComplaintEntity, Integer> {
    List<ComplaintEntity> findByState(String state);
    // Find complaints by client ID
    List<ComplaintEntity> findByClientEntityId(Integer clientId);

    // Find complaints by technician ID
    List<ComplaintEntity> findByTechnicianEntityId(Integer technicianId);

}
