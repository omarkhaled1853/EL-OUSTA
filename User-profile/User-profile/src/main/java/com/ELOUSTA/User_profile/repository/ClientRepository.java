package com.ELOUSTA.User_profile.repository;

import com.ELOUSTA.User_profile.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
