package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientCountRequest extends JpaRepository<ClientEntity, Integer> {
    @Query(value = "SELECT client.id, client.emailAddress, client.username," +
            " client.firstName, client.lastName, client.phoneNumber, temp.cnt " +
            "FROM Client client " +
            "JOIN (SELECT r.userId, COUNT(*) AS cnt " +
            "           FROM Request r " +
            "           GROUP BY r.userId) AS temp " +
            "ON client.id = temp.userId", nativeQuery = true)
    List<Object[]> findClientRequestCounts();
}
