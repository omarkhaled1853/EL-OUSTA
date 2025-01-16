package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Integer> {
    Optional<TechnicianEntity> findByUsername(String username);
    Optional<TechnicianEntity> findByEmailAddress(String emailAddress);
    @Query("SELECT t " +
            "FROM TechnicianEntity t " +
            "JOIN FETCH t.domainEntity d " +
            "LEFT JOIN FETCH t.portfolioEntities p " +
            "WHERE t.id = :technicianId")
    Optional<TechnicianEntity> findTechnicianWithDomainAndPortfolio(@Param("technicianId") Integer technicianId);

    @Modifying
    @Query("UPDATE TechnicianEntity t" +
            " SET t.profilePicture = NULL" +
            " WHERE t.id = :technicianId")
    void deleteProfilePictureById(@Param("technicianId") Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM PortfolioEntity p " +
            "WHERE p.id = :portfolioId AND p.technicianEntity.id = :technicianId")
    void deletePortfolioByIdAndTechnicianId(@Param("portfolioId") Integer portfolioId,
                                   @Param("technicianId") Integer technicianId);
}
