package com.ELOUSTA.ELOUSTA.backend.service.requestTest;


import com.ELOUSTA.ELOUSTA.backend.entity.*;
import com.ELOUSTA.ELOUSTA.backend.repository.*;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private RequestService requestService;

    private RequestEntity request;
    private DomainEntity domain;
    private AdminEntity admin;

    public RequestServiceTest() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        request = new RequestEntity();
        domain = new DomainEntity();
        admin = new AdminEntity();

        // Mocking data
        admin.setCanManipulateProfessions(true);
        admin.setCanAccessTechnician(true);
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
    void testRequestNumbers() {
        List<RequestEntity> requests = List.of(new RequestEntity());
        when(requestRepo.findAll()).thenReturn(requests);

        int result = requestService.requestnumbers();

        assertEquals(1, result);
        verify(requestRepo, times(1)).findAll();
    }

    @Test
    public void testSaveRequest() {
        when(requestRepo.save(any(RequestEntity.class))).thenReturn(request);

        RequestEntity savedRequest = requestService.Saverequest(request);

        assertNotNull(savedRequest);
        verify(requestRepo, times(1)).save(any(RequestEntity.class));
    }

    @Test
    public void testGetRequestService() {
        String state = "pending";
        when(requestRepo.getRequests(state)).thenReturn(Arrays.asList(request));

        List<RequestEntity> requests = requestService.getRequestService(state);

        assertNotNull(requests);
        assertEquals(1, requests.size());
        verify(requestRepo, times(1)).getRequests(state);
    }

    @Test
    public void testGetProfessions() {
        when(domainRepository.getall()).thenReturn(Arrays.asList(domain));

        List<DomainEntity> professions = requestService.getProfessions();

        assertNotNull(professions);
        assertEquals(1, professions.size());
        verify(domainRepository, times(1)).getall();
    }

    @Test
    public void testCanDomanipulatprofession() {
        when(adminRepository.findById(anyInt())).thenReturn(Optional.of(admin));

        boolean result = requestService.canDomanipulatprofession(1);

        assertTrue(result);
        verify(adminRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testCanDodelete() {
        when(adminRepository.findById(anyInt())).thenReturn(Optional.of(admin));

        boolean result = requestService.canDodelete(1);

        assertTrue(result);
        verify(adminRepository, times(1)).findById(anyInt());
    }
}
