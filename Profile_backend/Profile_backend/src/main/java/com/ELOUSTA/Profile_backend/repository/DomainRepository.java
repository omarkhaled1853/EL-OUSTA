package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {
}
