package com.ELOUSTA.ELOUSTA.backend.repository;


import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<RequestEntity,Integer> {
}
