package com.ELOUSTA.ELOUSTA.backend.service.AdminRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminRequestsDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.adminRequests.AdminRequestsMapper;
import com.ELOUSTA.ELOUSTA.backend.service.adminRequests.AdminRequestsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminRequestsServiceTesting {

    @InjectMocks
    private AdminRequestsService adminRequestsService;

    @Mock
    private RequestRepository repository;

    @Mock
    private AdminRequestsMapper mapper;

    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRequestsShouldReturnMappedRequests() throws Exception {
        // Setup test data
        String state = "IN-PROGRESS";
        List<RequestEntity> entities = new ArrayList<>();
        entities.add(new RequestEntity(1, 1, 1, state, "fixing leak", "LONDON",
                dateFormat.parse("01/01/2024"), dateFormat.parse("02/01/2024")));

        List<AdminRequestsDTO> DTOs = new ArrayList<>();
        DTOs.add(new AdminRequestsDTO("Tech1", "Client1", dateFormat.parse("01/01/2024"), dateFormat.parse("02/01/2024")));

        when(repository.getAllByState(state)).thenReturn(entities);
        when(mapper.mapRequestEntityToAdminRequestDTO(entities)).thenReturn(DTOs);


        List<AdminRequestsDTO> result = adminRequestsService.getRequests(state);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tech1", result.get(0).getTechnicianUserName());
    }

    @Test
    void searchRequests_ShouldReturnMappedRequests() throws Exception {
        // Setup test data
        String technicianUserName = "Tech1";
        String state = "IN-PROGRESS";

        List<RequestEntity> entities = new ArrayList<>();
        entities.add(new RequestEntity(1, 1, 1, state, "fixing leak", "LONDON",
                dateFormat.parse("01/01/2024"), dateFormat.parse("02/01/2024")));

        List<AdminRequestsDTO> DTOs = new ArrayList<>();
        DTOs.add(new AdminRequestsDTO("Tech1", "Client1", dateFormat.parse("01/01/2024"), dateFormat.parse("02/01/2024")));

        when(repository.searchByTechnicianUserName(technicianUserName, state)).thenReturn(entities);
        when(mapper.mapRequestEntityToAdminRequestDTO(entities)).thenReturn(DTOs);

        List<AdminRequestsDTO> result = adminRequestsService.searchRequests(technicianUserName, state);

        // Verify
        assertNotNull(result);
        assertEquals("Tech1", result.get(0).getTechnicianUserName());

    }

    @Test
    void getRequests_ShouldReturnEmptyList_WhenNoRequestsFound() {
        // Setup
        String state = "IN-PROGRESS";
        when(repository.getAllByState(state)).thenReturn(new ArrayList<>());
        when(mapper.mapRequestEntityToAdminRequestDTO(anyList())).thenReturn(new ArrayList<>());

        // Execute
        List<AdminRequestsDTO> result = adminRequestsService.getRequests(state);

        // Verify
        assertNotNull(result);
        assertTrue(result.isEmpty());

    }
}
