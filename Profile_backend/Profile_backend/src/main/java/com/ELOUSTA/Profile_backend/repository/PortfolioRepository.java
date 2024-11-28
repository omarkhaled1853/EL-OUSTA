package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
}
