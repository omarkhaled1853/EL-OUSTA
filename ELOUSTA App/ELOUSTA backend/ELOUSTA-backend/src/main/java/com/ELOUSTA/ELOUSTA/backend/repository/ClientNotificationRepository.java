package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.notification.ClientNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, Integer> {
    List<ClientNotification> findByClientEntity_Id(Integer client_id);
}
