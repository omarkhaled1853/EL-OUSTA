package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {
}
