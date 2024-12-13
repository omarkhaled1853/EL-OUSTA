package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity,Integer> {

    @Query("SELECT r FROM RequestEntity WHERE r.techId = :techId ORDER by r.startDate ASC")
    List<RequestEntity> sortRequestsByStartDate(@Param("techID") int techId);
    @Query("SELECT r FROM RequestEntity WHERE r.techId = :techId ORDER by r.endDate ASC")
    List<RequestEntity> sortRequestsByEndDate(@Param("techID") int techId);

}
