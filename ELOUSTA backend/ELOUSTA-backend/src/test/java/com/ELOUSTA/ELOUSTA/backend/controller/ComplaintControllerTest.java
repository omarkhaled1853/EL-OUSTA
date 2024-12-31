package com.ELOUSTA.ELOUSTA.backend.controller;

import com.ELOUSTA.ELOUSTA.backend.controller.Complaints.ComplaintController;
import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.ComplaintRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComplaintControllerTest {

    @InjectMocks
    private ComplaintController complaintController;

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllComplaints() {
        ComplaintEntity complaint = new ComplaintEntity();
        complaint.setId("1");
        complaint.setComplaintBody("Test complaint");
        complaint.setState("Pending");
        complaint.setDirection(0);

        ClientEntity client = new ClientEntity();
        client.setId(1);
        client.setFirstName("John");
        client.setLastName("Doe");
        complaint.setUser(client);

        TechnicianEntity technician = new TechnicianEntity();
        technician.setId(2);
        technician.setFirstName("Jane");
        technician.setLastName("Smith");
        complaint.setTech(technician);

        when(complaintRepository.findAll()).thenReturn(Collections.singletonList(complaint));

        List<ComplaintDTO> complaints = complaintController.getAllComplaints();

        assertEquals(1, complaints.size());
        ComplaintDTO dto = complaints.get(0);
        assertEquals("1", dto.getId());
        assertEquals("Test complaint", dto.getComplaintBody());
        assertEquals("Pending", dto.getState());
        assertEquals(0, dto.getDirection());
        assertEquals(1, dto.getClientId());
        assertEquals("John Doe", dto.getClientName());
        assertEquals(2, dto.getTechnicianId());
        assertEquals("Jane Smith", dto.getTechnicianName());
    }

    @Test
    void testGetComplaintById() {
        // Mock the ComplaintEntity
        ComplaintEntity complaint = new ComplaintEntity();
        complaint.setId("1");
        complaint.setComplaintBody("Test complaint");
        complaint.setState("Pending");
        complaint.setDirection(0);

        // Mock the ClientEntity
        ClientEntity client = new ClientEntity();
        client.setId(1);
        client.setFirstName("John");
        client.setLastName("Doe");
        complaint.setUser(client);

        // Mock the TechnicianEntity
        TechnicianEntity technician = new TechnicianEntity();
        technician.setId(2);
        technician.setFirstName("Jane");
        technician.setLastName("Smith");
        complaint.setTech(technician);

        // Mock the repository behavior
        when(complaintRepository.findById("1")).thenReturn(Optional.of(complaint));

        // Perform the test
        ResponseEntity<ComplaintDTO> response = complaintController.getComplaintById("1");

        // Assertions
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());

        ComplaintDTO dto = response.getBody();
        assertEquals("1", dto.getId());
        assertEquals("Test complaint", dto.getComplaintBody());
        assertEquals("Pending", dto.getState());
        assertEquals(0, dto.getDirection());
        assertEquals(1, dto.getClientId());
        assertEquals("John Doe", dto.getClientName());
        assertEquals(2, dto.getTechnicianId());
        assertEquals("Jane Smith", dto.getTechnicianName());
    }

    @Test
    void testDeleteComplaint() {
        when(complaintRepository.existsById("1")).thenReturn(true);

        ResponseEntity<Void> response = complaintController.deleteComplaint("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(complaintRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteComplaintNotFound() {
        when(complaintRepository.existsById("1")).thenReturn(false);

        ResponseEntity<Void> response = complaintController.deleteComplaint("1");

        assertEquals(404, response.getStatusCodeValue());
        verify(complaintRepository, never()).deleteById("1");
    }

    @Test
    void testDeleteUser() {
        when(clientRepository.existsById(1)).thenReturn(true);
        when(complaintRepository.findByUserId(1)).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = complaintController.deleteUser(1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("User and associated complaints deleted successfully.", response.getBody());
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUserNotFound() {
        when(clientRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = complaintController.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found.", response.getBody());
        verify(clientRepository, never()).deleteById(1);
    }

    @Test
    void testGetClientAndTechnicianNames() {
        ComplaintEntity complaint = new ComplaintEntity();
        ClientEntity client = new ClientEntity();
        client.setFirstName("John");
        client.setLastName("Doe");
        complaint.setUser(client);

        TechnicianEntity technician = new TechnicianEntity();
        technician.setFirstName("Jane");
        technician.setLastName("Smith");
        complaint.setTech(technician);

        when(complaintRepository.findById("1")).thenReturn(Optional.of(complaint));

        ResponseEntity<Map<String, String>> response = complaintController.getClientAndTechnicianNames("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().get("Client"));
        assertEquals("Jane Smith", response.getBody().get("Technician"));
    }
}
