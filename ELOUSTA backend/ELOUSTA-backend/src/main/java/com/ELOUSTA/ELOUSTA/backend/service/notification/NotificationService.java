package com.ELOUSTA.ELOUSTA.backend.service.notification;

import com.ELOUSTA.ELOUSTA.backend.entity.*;
import com.ELOUSTA.ELOUSTA.backend.entity.notification.ClientNotification;
import com.ELOUSTA.ELOUSTA.backend.entity.notification.TechnicianNotification;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientNotificationRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianNotificationRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// Service class for handling notifications
@Service
public class NotificationService {

    // Repositories to interact with the database for saving and retrieving notifications
    @Autowired
    private ClientNotificationRepository clientNotificationRepository;

    @Autowired
    private TechnicianNotificationRepository technicianNotificationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    // Spring's messaging template for sending messages over websockets
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Sends a notification to a specific client.
     * It also saves the notification to the database and sends it via WebSocket to the client.
     *
     * @param message - the content of the notification
     * @param clientId - the unique identifier of the client to receive the notification
     */
    public void sendNotificationToClient(String message, int clientId) {
        // WebSocket destination path for the client (for real-time message delivery)
        String destination = "/subscribe/client/" + clientId;

        ClientNotification notification = ClientNotification.builder()
                .message(message)
                .date(new Date()) // Set the current date as the notification date
                .build();
        // Save the notification to the database
        saveClientNotification(message, clientId);

        // Send the message to the client's WebSocket destination
        messagingTemplate.convertAndSend(destination, notification);
    }

    /**
     * Sends a notification to a specific technician.
     * It also saves the notification to the database and sends it via WebSocket to the technician.
     *
     * @param message - the content of the notification
     * @param technicianId - the unique identifier of the technician to receive the notification
     */
    public void sendNotificationToTechnician(String message, int technicianId) {
        // WebSocket destination path for the technician (for real-time message delivery)
        String destination = "/subscribe/tech/" + technicianId;

        TechnicianNotification notification = TechnicianNotification.builder()
                .message(message)
                .date(new Date()) // Set the current date as the notification date
                .build();

        // Save the notification to the database
        saveTechnicianNotification(message, technicianId);

        // Send the message to the technician's WebSocket destination
        messagingTemplate.convertAndSend(destination, notification);
    }

    /**
     * Retrieves a list of notifications for a specific client.
     *
     * @param clientId - the unique identifier of the client
     * @return - a list of notifications for the client
     */
    public List<ClientNotification> getClientNotifications(int clientId) {
        return clientNotificationRepository.findByClientEntity_Id(clientId);
    }

    /**
     * Retrieves a list of notifications for a specific technician.
     *
     * @param technicianId - the unique identifier of the technician
     * @return - a list of notifications for the technician
     */
    public List<TechnicianNotification> getTechnicianNotifications(int technicianId) {
        return technicianNotificationRepository.findByTechnicianEntity_Id(technicianId);
    }

    /**
     * Saves a notification for a client in the database.
     *
     * @param message - the content of the notification
     * @param client_id - the unique identifier of the client
     */
    private void saveClientNotification(String message, int client_id) {
        // Retrieve the client entity from the database
        ClientEntity clientEntity = clientRepository.findById(client_id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Build the notification object for the client
        ClientNotification clientNotification = ClientNotification.builder()
                .clientEntity(clientEntity)
                .message(message)
                .date(new Date()) // Set the current date as the notification date
                .build();

        // Save the notification to the database
        clientNotificationRepository.save(clientNotification);
    }

    /**
     * Saves a notification for a technician in the database.
     *
     * @param message - the content of the notification
     * @param technician_id - the unique identifier of the technician
     */
    private void saveTechnicianNotification(String message, int technician_id) {
        // Retrieve the technician entity from the database
        TechnicianEntity technicianEntity = technicianRepository.findById(technician_id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        // Build the notification object for the technician
        TechnicianNotification technicianNotification = TechnicianNotification.builder()
                .technicianEntity(technicianEntity)
                .message(message)
                .build();

        // Save the notification to the database
        technicianNotificationRepository.save(technicianNotification);
    }
}
