package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientFilterRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsFiltering.ClientLocationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientFilterRequestsServiceTest {
    @Mock
    private ClientLocationFilter clientLocationFilter;

    @InjectMocks
    private ClientFilterRequestsService clientFilterRequestsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFilterRequests() {
        // Arrange
        int id = 1;
        String state = "PENDING";
        String query = "Location A";

        // Mocked RequestEntity list
        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("PENDING").description("Request 1")
                .Location("Location A").build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("PENDING").description("Request 2")
                .Location("Location A").build();

        List<RequestEntity> requestEntityList = Arrays.asList(request1, request2);

        // Expected DTO list
        List<ClientRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(requestEntityList);

        // Mock behavior of LocationFilter
        when(clientLocationFilter.Filter(id, state, query)).thenReturn(requestEntityList);

        // Act
        List<ClientRequestDTO> actualDTOList = clientFilterRequestsService.filterRequests(id, state, query);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);

        // Verify that the filter method was called exactly once
        verify(clientLocationFilter, times(1)).Filter(id, state, query);
    }
}
