package com.ELOUSTA.ELOUSTA.backend.service.notification;

import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.notification.ClientNotification;
import com.ELOUSTA.ELOUSTA.backend.entity.notification.TechnicianNotification;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientNotificationRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianNotificationRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class NotificationServiceTest {

    @Mock
    private ClientNotificationRepository clientNotificationRepository;
    @Mock
    private TechnicianNotificationRepository technicianNotificationRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private TechnicianRepository technicianRepository;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @InjectMocks
    private NotificationService notificationService;


    private final int clientId = 1;
    private final int technicianId = 1;
    private final String testMessage = "Test notification message";


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotificationToClientTest(){
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new ClientEntity()));

        notificationService.sendNotificationToClient(testMessage, clientId);

        verify(clientNotificationRepository, times(1)).save(any(ClientNotification.class));

        verify(messagingTemplate, times(1)).convertAndSend(eq("/subscribe/client/" + clientId), any(ClientNotification.class));
    }
    @Test
    void sendNotificationToTechnicianTest(){
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(new TechnicianEntity()));

        notificationService.sendNotificationToTechnician(testMessage, technicianId);

        verify(technicianNotificationRepository, times(1)).save(any(TechnicianNotification.class));

        verify(messagingTemplate, times(1)).convertAndSend(eq("/subscribe/tech/" + technicianId), any(TechnicianNotification.class));
    }

    @Test
    void getClientNotificationsTest(){
        List<ClientNotification> mockNotifications = List.of(new ClientNotification());
        when(clientNotificationRepository.findByClientEntity_Id(clientId)).thenReturn(mockNotifications);

        List<ClientNotification> notifications = notificationService.getClientNotifications(clientId);

        assertNotNull(notifications);
        assertEquals(1, notifications.size());
    }

    @Test
    void getTechnicianNotificationsTest(){
        List<TechnicianNotification> mockNotifications = List.of(new TechnicianNotification());
        when(technicianNotificationRepository.findByTechnicianEntity_Id(technicianId)).thenReturn(mockNotifications);

        List<TechnicianNotification> notifications = notificationService.getTechnicianNotifications(technicianId);

        assertNotNull(notifications);
        assertEquals(1, notifications.size());
    }

}
