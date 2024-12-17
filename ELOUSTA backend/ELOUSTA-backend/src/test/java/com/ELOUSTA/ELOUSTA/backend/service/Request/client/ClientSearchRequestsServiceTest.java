package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientSearchRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSearch.ClientRequestsSearch;
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

public class ClientSearchRequestsServiceTest {

    @Mock
    private ClientRequestsSearch clientRequestsSearch;

    @InjectMocks
    private ClientSearchRequestsService clientSearchRequestsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchRequests() {
        // Arrange
        int id = 1;
        String state = "COMPLETED";
        String query = "Search Query";

        // Mocked RequestEntity list
        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("COMPLETED").description("Request 1")
                .Location("Location A").build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("COMPLETED").description("Request 2")
                .Location("Location B").build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);

        // Expected DTO list
        List<ClientRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(mockRequestEntityList);

        // Mock behavior of RequestsSearch
        when(clientRequestsSearch.searchRequests(id, state, query)).thenReturn(mockRequestEntityList);

        // Act
        List<ClientRequestDTO> actualDTOList = clientSearchRequestsService.searchRequests(id, state, query);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        assertEquals(expectedDTOList.size(), actualDTOList.size());

        // Verify that the search method was called exactly once
        verify(clientRequestsSearch, times(1)).searchRequests(id, state, query);
    }
}
