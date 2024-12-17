package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.impl.ClientRequestsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.service.clientRequests.RequestsTestData.testInProgressRequestEntityList;
import static com.ELOUSTA.ELOUSTA.backend.service.clientRequests.RequestsTestData.testPendingRequestEntityList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientRequestsServiceImplTest {
    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private ClientRequestsServiceImpl clientRequestService; // The service you are testing

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientPendingRequestsWithExistRequests() {
        int clientId = 1;
        String pendingState = "PENDING";

        List<RequestEntity> requestEntityList = testPendingRequestEntityList();

        when(requestRepository.getClientRequestsByState(clientId, pendingState)).thenReturn(requestEntityList);

        List<ClientRequestDTO> clientRequestDTOList = clientRequestService.getClientPendingRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, pendingState);

        assertEquals(2, clientRequestDTOList.size());
        assertEquals(1, clientRequestDTOList.getFirst().getId());
        assertEquals(101, clientRequestDTOList.getFirst().getTechId());
        assertEquals("PENDING", clientRequestDTOList.getFirst().getState());
        assertEquals("Request 1", clientRequestDTOList.getFirst().getDescription());
        assertEquals("Location A", clientRequestDTOList.getFirst().getLocation());
    }

    @Test
    void testGetClientPendingRequestsWithNoExistRequests() {
        int clientId = 1;
        String pendingState = "PENDING";

        List<RequestEntity> pendingRequestEntityList = List.of();

        when(requestRepository.getClientRequestsByState(clientId, pendingState)).thenReturn(pendingRequestEntityList);

        List<ClientRequestDTO> clientRequestDTOList = clientRequestService.getClientPendingRequests(clientId);

        verify(requestRepository).getClientRequestsByState(clientId, pendingState);

        assertEquals(0, clientRequestDTOList.size());
    }

}
