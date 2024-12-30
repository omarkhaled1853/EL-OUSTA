package com.ELOUSTA.ELOUSTA.backend.service.request.client;

import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestStatusPayload;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.ELOUSTA.ELOUSTA.backend.service.request.client.RequestsTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientRequestActionServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientRequestService clientRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDoneRequestSuccess() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testPendingRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        // Act
        clientRequestService.doneRequest(requestStatusPayload);

        // Assert
        assertEquals("COMPLETED", request.getState());
        verify(requestRepository).save(request);
        verify(notificationService).
                sendNotificationToClient("JohnDoe Done the request ", 101);
    }

    @Test
    void testDoneRequestNotFound() {
        // Arrange
        RequestStatusPayload payload = RequestStatusPayload.builder()
                .id(1)
                .build();

        when(requestRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clientRequestService.doneRequest(payload);
        });

        assertEquals("NO such Data", exception.getMessage());
    }

    @Test
    void testDoneCompletedRequestFail() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testCompletedRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));


        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            clientRequestService.doneRequest(requestStatusPayload);
        });

        assertEquals("Request cannot be done in its current state: COMPLETED", exception.getMessage());
    }

    @Test
    void testDonePendingRequestFail() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testPendingRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));


        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            clientRequestService.doneRequest(requestStatusPayload);
        });

        assertEquals("Request cannot be done in its current state: PENDING", exception.getMessage());
    }

    @Test
    void testCancelPendingRequestSuccess() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testPendingRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        // Act
        clientRequestService.cancelRequest(requestStatusPayload);

        // Assert
        assertEquals("CANCELLED", request.getState());
        verify(requestRepository).save(request);
        verify(notificationService).
                sendNotificationToClient("JohnDoe Cancel the request ", 101);
    }

    @Test
    void testCancelInProgressRequestSuccess() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testInProgressRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        // Act
        clientRequestService.cancelRequest(requestStatusPayload);

        // Assert
        assertEquals("CANCELLED", request.getState());
        verify(requestRepository).save(request);
        verify(notificationService).
                sendNotificationToClient("JohnDoe Cancel the request ", 101);
    }

    @Test
    void testCancelCompletedRequestFail() {
        RequestStatusPayload requestStatusPayload = RequestStatusPayload.builder()
                .id(1)
                .clientId(1)
                .techId(101)
                .build();

        ClientEntity client = new ClientEntity();
        client.setUsername("JohnDoe");

        RequestEntity request = testCompletedRequestEntityList().getFirst();

        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));


        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            clientRequestService.cancelRequest(requestStatusPayload);
        });

        assertEquals("Request cannot be cancelled in its current state: COMPLETED", exception.getMessage());
    }

}
