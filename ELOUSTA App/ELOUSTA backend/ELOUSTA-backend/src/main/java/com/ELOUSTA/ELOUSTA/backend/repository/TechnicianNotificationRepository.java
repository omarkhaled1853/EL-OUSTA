package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.notification.TechnicianNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnicianNotificationRepository extends JpaRepository<TechnicianNotification, Integer> {
    List<TechnicianNotification> findByTechnicianEntity_Id(Integer technicianId);
}
