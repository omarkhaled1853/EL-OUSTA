package com.ELOUSTA.ELOUSTA.backend.repository;
import java.util.Optional;

import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {

    @Query(value = "SELECT * FROM DOMAIN WHERE name= :Dname",nativeQuery = true)
    DomainEntity getDomainByName(@Param("Dname")String Dname);
    Optional<DomainEntity> findByName(String name);
    @Query(value = "SELECT * FROM DOMAIN WHERE name= :Dname", nativeQuery = true)
    List<DomainEntity> getDomains(@Param("Dname") String Dname);


}
