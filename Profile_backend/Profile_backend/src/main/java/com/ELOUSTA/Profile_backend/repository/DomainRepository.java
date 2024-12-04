package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {
}
