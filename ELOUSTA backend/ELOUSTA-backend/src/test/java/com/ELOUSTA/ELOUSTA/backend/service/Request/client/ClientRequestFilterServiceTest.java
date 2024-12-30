package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.ClientRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.ClientRequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
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

public class ClientRequestFilterServiceTest {
    @Mock
    private ClientRequestFilter clientRequestFilter;

    @InjectMocks
    private ClientRequestFilterService requestFilterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFilterRequests() {
        // Arrange
        RequestPayload requestPayload = RequestPayload.builder()
                .id(1)
                .query("Location A")
                .state("PENDING")
                .build();

        // Mocked RequestEntity list
        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("PENDING").description("Request 1")
                .Location("Location A").build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("PENDING").description("Request 2")
                .Location("Location A").build();

        List<RequestEntity> requestEntityList = Arrays.asList(request1, request2);

        // Expected DTO list
        List<ViewRequestDTO> expectedDTOList = RequestEntityListToClientRequestDTOList(requestEntityList);

        // Mock behavior of LocationFilter
        when(clientRequestFilter
                .Filter(requestPayload.getId(), requestPayload.getState(), requestPayload.getQuery()))
                .thenReturn(requestEntityList);

        // Act
        List<ViewRequestDTO> actualDTOList = requestFilterService.filterRequests(requestPayload);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);

        // Verify that the filter method was called exactly once
        verify(clientRequestFilter, times(1))
                .Filter(requestPayload.getId(), requestPayload.getState(), requestPayload.getQuery());
    }
}
