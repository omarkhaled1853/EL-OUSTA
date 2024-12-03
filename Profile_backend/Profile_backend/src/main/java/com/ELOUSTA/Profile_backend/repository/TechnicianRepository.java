package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Integer> {
    @Query("SELECT t " +
            "FROM TechnicianEntity t " +
            "JOIN FETCH t.domainEntity d " +
            "LEFT JOIN FETCH t.portfolioEntities p " +
            "WHERE t.id = :technicianId")
    TechnicianEntity findTechnicianWithDomainAndPortfolio(@Param("technicianId") Integer technicianId);
}
