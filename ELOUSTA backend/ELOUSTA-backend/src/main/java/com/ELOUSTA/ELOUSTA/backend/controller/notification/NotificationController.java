package com.ELOUSTA.ELOUSTA.backend.controller.notification;

import com.ELOUSTA.ELOUSTA.backend.entity.notification.ClientNotification;
import com.ELOUSTA.ELOUSTA.backend.entity.notification.TechnicianNotification;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Endpoint to get notifications for a specific client
    @GetMapping("/client/notifications/{clientId}")
    public List<ClientNotification> getClientNotifications(@PathVariable int clientId) {
        System.out.println(clientId);
        return notificationService.getClientNotifications(clientId);
    }
    // Endpoint to get notifications for a specific technician
    @GetMapping("/tech/notifications/{technicianId}")
    public List<TechnicianNotification> getTechnicianNotifications(@PathVariable int technicianId) {
        return notificationService.getTechnicianNotifications(technicianId);
    }
}

