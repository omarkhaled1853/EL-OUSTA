package com.ELOUSTA.ELOUSTA.backend.repository;


import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepo extends JpaRepository<RequestEntity,Integer> {
    @Query(value = "SELECT * FROM REQUEST WHERE STATE = :state", nativeQuery = true)
    List<RequestEntity> getRequests(@Param("state") String state);
}
