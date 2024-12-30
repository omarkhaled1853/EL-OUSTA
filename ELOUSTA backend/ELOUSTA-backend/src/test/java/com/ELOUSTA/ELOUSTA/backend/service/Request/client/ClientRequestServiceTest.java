package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.ClientRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.service.Request.client.RequestsTestData.*;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityToOrderRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientRequestServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private ClientRequestService clientRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRequest() {
        // Create a sample RequestEntity
        OrderRequestDTO sampleRequest = new OrderRequestDTO();
        sampleRequest.setClientId(1);
        sampleRequest.setTechId(2);
        sampleRequest.setDescription("Test Description");
        sampleRequest.setLocation("Test Location");
        sampleRequest.setState("PENDING");

        RequestEntity expectedRequestEntity  = RequestEntityToOrderRequestDTO(sampleRequest);

        // Call the service method
        clientRequestService.addRequest(sampleRequest);

        // Assert
        ArgumentCaptor<RequestEntity> captor = ArgumentCaptor.forClass(RequestEntity.class);
        verify(requestRepository, times(1)).save(captor.capture());

        RequestEntity actualRequestEntity = captor.getValue();

        assertEquals(expectedRequestEntity.getUserId(), actualRequestEntity.getUserId());
        assertEquals(expectedRequestEntity.getTechId(), actualRequestEntity.getTechId());
        assertEquals(expectedRequestEntity.getDescription(), actualRequestEntity.getDescription());
        assertEquals(expectedRequestEntity.getLocation(), actualRequestEntity.getLocation());
        assertEquals(expectedRequestEntity.getState(), actualRequestEntity.getState());
    }

    @Test
    void testGetPendingRequestsWithExistRequests() {
        int clientId = 1;
        String pendingState = "PENDING";

        List<RequestEntity> requestEntityList = testPendingRequestEntityList();

        when(requestRepository.getClientRequestsByState(clientId, pendingState)).thenReturn(requestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getPendingRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, pendingState);

        assertEquals(2, viewRequestDTOList.size());
        assertEquals(1, viewRequestDTOList.getFirst().getId());
        assertEquals(101, viewRequestDTOList.getFirst().getTechId());
        assertEquals("PENDING", viewRequestDTOList.getFirst().getState());
        assertEquals("Request 1", viewRequestDTOList.getFirst().getDescription());
        assertEquals("Location A", viewRequestDTOList.getFirst().getLocation());
    }

    @Test
    void testGetPendingRequestsWithNoExistRequests() {
        int clientId = 1;
        String pendingState = "PENDING";

        List<RequestEntity> pendingRequestEntityList = List.of();

        when(requestRepository.getClientRequestsByState(clientId, pendingState)).thenReturn(pendingRequestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getPendingRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, pendingState);

        assertEquals(0, viewRequestDTOList.size());
    }

    @Test
    void testGetInProgressRequestsWithExistRequests() {
        int clientId = 1;
        String inProgressState = "IN-PROGRESS";

        List<RequestEntity> requestEntityList = testInProgressRequestEntityList();

        when(requestRepository.getClientRequestsByState(clientId, inProgressState)).thenReturn(requestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getInProgressRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, inProgressState);

        assertEquals(2, viewRequestDTOList.size());
        assertEquals(1, viewRequestDTOList.getFirst().getId());
        assertEquals(101, viewRequestDTOList.getFirst().getTechId());
        assertEquals("IN-PROGRESS", viewRequestDTOList.getFirst().getState());
        assertEquals("Request 1", viewRequestDTOList.getFirst().getDescription());
        assertEquals("Location A", viewRequestDTOList.getFirst().getLocation());
    }

    @Test
    void testGetInProgressRequestsWithNoExistRequests() {
        int clientId = 1;
        String inProgressState = "IN-PROGRESS";

        List<RequestEntity> pendingRequestEntityList = List.of();

        when(requestRepository.getClientRequestsByState(clientId, inProgressState)).thenReturn(pendingRequestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getInProgressRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, inProgressState);

        assertEquals(0, viewRequestDTOList.size());
    }

    @Test
    void testGetCompletedRequestsWithExistRequests() {
        int clientId = 1;
        String completedState = "COMPLETED";

        List<RequestEntity> requestEntityList = testCompletedRequestEntityList();

        when(requestRepository.getClientRequestsByState(clientId, completedState)).thenReturn(requestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getCompletedRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, completedState);

        assertEquals(2, viewRequestDTOList.size());
        assertEquals(1, viewRequestDTOList.getFirst().getId());
        assertEquals(101, viewRequestDTOList.getFirst().getTechId());
        assertEquals("COMPLETED", viewRequestDTOList.getFirst().getState());
        assertEquals("Request 1", viewRequestDTOList.getFirst().getDescription());
        assertEquals("Location A", viewRequestDTOList.getFirst().getLocation());
    }

    @Test
    void testGetCompletedRequestsWithNoExistRequests() {
        int clientId = 1;
        String completedState = "COMPLETED";

        List<RequestEntity> pendingRequestEntityList = List.of();

        when(requestRepository.getClientRequestsByState(clientId, completedState)).thenReturn(pendingRequestEntityList);

        List<ViewRequestDTO> viewRequestDTOList = clientRequestService.getCompletedRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, completedState);

        assertEquals(0, viewRequestDTOList.size());
    }

}
