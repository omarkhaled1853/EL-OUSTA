package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByEmailAddress(String emailAddress);

    void deleteByUsername (String username);

    @Modifying
    @Query("UPDATE ClientEntity c" +
            " SET c.profilePicture = NULL" +
            " WHERE c.id = :clientId")
    void deleteProfilePictureById(@Param("clientId") Integer id);

}
