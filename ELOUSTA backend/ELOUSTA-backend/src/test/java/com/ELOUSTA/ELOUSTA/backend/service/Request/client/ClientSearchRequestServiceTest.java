package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.ClientRequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.ClientRequestSearch;
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

public class ClientSearchRequestServiceTest {

    @Mock
    private ClientRequestSearch clientRequestSearch;

    @InjectMocks
    private ClientRequestSearchService clientRequestSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchRequests() {
        // Arrange
        int id = 1;
        RequestPayload requestPayload = RequestPayload.builder()
                .state("COMPLETED")
                .query("Search Query")
                .build();

        // Mocked RequestEntity list
        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("COMPLETED").description("Request 1")
                .Location("Location A").build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("COMPLETED").description("Request 2")
                .Location("Location B").build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);

        // Expected DTO list
        List<ViewRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(mockRequestEntityList);

        // Mock behavior of RequestsSearch
        when(clientRequestSearch.search(id, requestPayload.getState(), requestPayload.getQuery()))
                .thenReturn(mockRequestEntityList);

        // Act
        List<ViewRequestDTO> actualDTOList = clientRequestSearchService.searchRequests(id, requestPayload);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        assertEquals(expectedDTOList.size(), actualDTOList.size());

        // Verify that the search method was called exactly once
        verify(clientRequestSearch, times(1))
                .search(id, requestPayload.getState(), requestPayload.getQuery());
    }
}
