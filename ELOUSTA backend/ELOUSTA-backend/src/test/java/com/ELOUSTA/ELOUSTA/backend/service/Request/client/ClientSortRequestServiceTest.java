package com.ELOUSTA.ELOUSTA.backend.service.Request.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByStartDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ClientSortRequestServiceTest {
    @Mock
    private ClientRequestSortByEndDate clientRequestSortByEndDate;

    @Mock
    private ClientRequestSortByStartDate clientRequestSortByStartDate;

    @InjectMocks
    private ClientRequestSortService clientRequestSortService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSortRequestsByStartDate() {
        // Arrange
        RequestPayload requestPayload = RequestPayload.builder()
                .id(1)
                .query("startDate")
                .state("PENDING")
                .build();

        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("PENDING").description("Request 1")
                .startDate(null).endDate(null).build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("PENDING").description("Request 2")
                .startDate(null).endDate(null).build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);
        List<ViewRequestDTO> expectedDTOList =
                RequestEntityListToViewRequestDTOList(mockRequestEntityList);

        when(clientRequestSortByStartDate.sort(requestPayload.getId(), requestPayload.getState()))
                .thenReturn(mockRequestEntityList);

        // Act
        List<ViewRequestDTO> actualDTOList = clientRequestSortService.sortRequests(requestPayload);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        verify(clientRequestSortByStartDate, times(1))
                .sort(requestPayload.getId(), requestPayload.getState());
        verify(clientRequestSortByEndDate, never()).sort(anyInt(), anyString());
    }

    @Test
    void testSortRequestsByEndDate() {
        // Arrange
        RequestPayload payload = RequestPayload.builder()
                .id(1)
                .state("COMPLETED")
                .query("endDate")
                .build();

        RequestEntity request1 = RequestEntity.builder()
                .id(1).userId(100).techId(200).state("COMPLETED").description("Request 1")
                .startDate(null).endDate(null).build();

        RequestEntity request2 = RequestEntity.builder()
                .id(2).userId(101).techId(201).state("COMPLETED").description("Request 2")
                .startDate(null).endDate(null).build();

        List<RequestEntity> mockRequestEntityList = Arrays.asList(request1, request2);
        List<ViewRequestDTO> expectedDTOList =
                RequestEntityListToViewRequestDTOList(mockRequestEntityList);

        when(clientRequestSortByEndDate.sort(payload.getId(), payload.getState()))
                .thenReturn(mockRequestEntityList);

        // Act
        List<ViewRequestDTO> actualDTOList = clientRequestSortService.sortRequests(payload);

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
        verify(clientRequestSortByEndDate, times(1))
                .sort(payload.getId(), payload.getState());
        verify(clientRequestSortByStartDate, never()).sort(anyInt(), anyString());
    }

    @Test
    void testSortRequestsInvalidType() {
        // Arrange
        RequestPayload requestPayload = RequestPayload.builder()
                .id(1)
                .query("invalidType")
                .state("PENDING")
                .build();

        // Act
        List<ViewRequestDTO> actualDTOList = clientRequestSortService.sortRequests(requestPayload);

        // Assert
        assertNull(actualDTOList);
        verify(clientRequestSortByStartDate, never()).sort(anyInt(), anyString());
        verify(clientRequestSortByEndDate, never()).sort(anyInt(), anyString());
    }
}
