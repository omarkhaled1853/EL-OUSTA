package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientSortRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSorting.ClientSortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSorting.ClientSortByStartDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ClientSortRequestsServiceTest {
    @Mock
    private ClientSortByEndDate clientSortByEndDate;

    @Mock
    private ClientSortByStartDate clientSortByStartDate;

    @InjectMocks
    private ClientSortRequestsService clientSortRequestsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSortRequestsByStartDate() {
        // Arrange
        int id = 1;
        String type = "startDate";
        String state = "PENDING";

        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state(state).description("Request 1")
                .startDate(null).endDate(null).build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state(state).description("Request 2")
                .startDate(null).endDate(null).build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);
        List<ClientRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(mockRequestEntityList);

        when(clientSortByStartDate.sort(id, state)).thenReturn(mockRequestEntityList);

        // Act
        List<ClientRequestDTO> actualDTOList = clientSortRequestsService.sortRequests(id, type, state);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        verify(clientSortByStartDate, times(1)).sort(id, state);
        verify(clientSortByEndDate, never()).sort(anyInt(), anyString());
    }

    @Test
    void testSortRequestsByEndDate() {
        // Arrange
        int id = 1;
        String type = "endDate";
        String state = "COMPLETED";

        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state(state).description("Request 1")
                .startDate(null).endDate(null).build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state(state).description("Request 2")
                .startDate(null).endDate(null).build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);
        List<ClientRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(mockRequestEntityList);

        when(clientSortByEndDate.sort(id, state)).thenReturn(mockRequestEntityList);

        // Act
        List<ClientRequestDTO> actualDTOList = clientSortRequestsService.sortRequests(id, type, state);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        verify(clientSortByEndDate, times(1)).sort(id, state);
        verify(clientSortByStartDate, never()).sort(anyInt(), anyString());
    }

    @Test
    void testSortRequestsInvalidType() {
        // Arrange
        int id = 1;
        String type = "invalidType";
        String state = "PENDING";

        // Act
        List<ClientRequestDTO> actualDTOList = clientSortRequestsService.sortRequests(id, type, state);

        // Assert
        assertNull(actualDTOList);
        verify(clientSortByStartDate, never()).sort(anyInt(), anyString());
        verify(clientSortByEndDate, never()).sort(anyInt(), anyString());
    }
}