package com.ELOUSTA.Profile_backend.repository;

import com.ELOUSTA.Profile_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
