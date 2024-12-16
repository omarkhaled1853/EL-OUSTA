package com.ELOUSTA.ELOUSTA.backend.service.requestTest;


import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepo;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    @Mock
    private RequestRepo requestRepo;

    @InjectMocks
    private RequestService requestService;

    public RequestServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaverequest() {
        // Create a sample RequestEntity
        RequestEntity sampleRequest = new RequestEntity();
        sampleRequest.setUserId(1);
        sampleRequest.setTechId(2);
        sampleRequest.setDescription("Test Description");
        sampleRequest.setLocation("Test Location");
        sampleRequest.setState("Pending");

        // Mock the behavior of the repository
        when(requestRepo.save(sampleRequest)).thenReturn(sampleRequest);

        // Call the service method
        RequestEntity savedRequest = requestService.Saverequest(sampleRequest);

        // Verify the result
        assertEquals(sampleRequest, savedRequest);

        // Verify that the save method of the repository was called exactly once
        verify(requestRepo, times(1)).save(sampleRequest);
    }
}
