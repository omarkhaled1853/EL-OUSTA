package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
}
