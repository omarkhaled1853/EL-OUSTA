package com.ELOUSTA.ELOUSTA.backend.service.requestTest;


import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepo;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    @Mock
    private RequestRepo requestRepo;

    @Mock
    private DomainRepository domainRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private ClientRepository clientRepository;

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
    @Test
    void testDeleteTech() {
        int techId = 1;
        doNothing().when(technicianRepository).deleteById(techId);

        requestService.deletetech(techId);

        verify(technicianRepository, times(1)).deleteById(techId);
    }

    @Test
    void testTechNumbers() {
        List<TechnicianEntity> technicians = List.of(new TechnicianEntity());
        when(technicianRepository.findAll()).thenReturn(technicians);

        int result = requestService.technumbers();

        assertEquals(1, result);
        verify(technicianRepository, times(1)).findAll();
    }

    @Test
    void testClientNumbers() {
        List<?> clients = List.of(new Object());
        when(clientRepository.findAll()).thenReturn((List<ClientEntity>) clients);

        int result = requestService.clientnumbers();

        assertEquals(1, result);
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testComplainsNumbers() {
        int result = requestService.complainsnumbers();

        assertEquals(62, result); // Testing the hardcoded value
    }

    @Test
    void testRequestNumbers() {
        List<RequestEntity> requests = List.of(new RequestEntity());
        when(requestRepo.findAll()).thenReturn(requests);

        int result = requestService.requestnumbers();

        assertEquals(1, result);
        verify(requestRepo, times(1)).findAll();
    }
}
