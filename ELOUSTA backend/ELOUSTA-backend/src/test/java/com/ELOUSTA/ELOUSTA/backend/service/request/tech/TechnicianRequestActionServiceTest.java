package com.ELOUSTA.ELOUSTA.backend.service.request.tech;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ComplaintRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TechnicianRequestActionServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private ComplaintRepository complaintRepository;

    @InjectMocks
    private TechnicianRequestService technicianRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComplaintSuccess() {
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .clientId(1)
                .techId(2)
                .complaintBody("Issue with client")
                .build();

        TechnicianEntity technicianEntity = new TechnicianEntity();
        technicianEntity.setId(2);
        technicianEntity.setUsername("JohnDoe");

        // Mock repository and service responses
        when(technicianRepository.findById(complaintDTO.getTechId()))
                .thenReturn(Optional.of(technicianEntity));

        // Act
        technicianRequestService.addComplaint(complaintDTO);

        // Verify interactions
        verify(complaintRepository, times(1)).save(any(ComplaintEntity.class));
        verify(notificationService, times(1))
                .sendNotificationToClient("JohnDoe Complains you ", complaintDTO.getClientId());
    }

    @Test
    void testAddComplaintTechnicianNotFound() {
        ComplaintDTO complaintDTO = ComplaintDTO.builder()
                .clientId(1)
                .techId(2)
                .complaintBody("Issue with client")
                .build();


        // Mock repository response for technician not found
        when(technicianRepository.findById(complaintDTO.getTechId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> technicianRequestService.addComplaint(complaintDTO));


        assertEquals("NO such Data", exception.getMessage());

        // Verify no interaction with other dependencies
        verify(complaintRepository, never()).save(any(ComplaintEntity.class));
        verify(notificationService, never()).sendNotificationToClient(anyString(), anyInt());
    }
}
