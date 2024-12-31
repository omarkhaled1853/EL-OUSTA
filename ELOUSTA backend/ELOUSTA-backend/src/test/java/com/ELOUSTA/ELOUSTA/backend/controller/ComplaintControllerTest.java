package com.ELOUSTA.ELOUSTA.backend.controller;

import com.ELOUSTA.ELOUSTA.backend.controller.Complaints.ComplaintController;
import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
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
    private AdminRepository adminRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to mock admin permissions
    private AdminEntity mockAdminEntity(boolean canAccessComplaints) {
        AdminEntity admin = new AdminEntity();
        admin.setId(1);
        admin.setUsername("admin");
        admin.setCanAccessComplaints(canAccessComplaints);
        return admin;
    }

    @Test
    void testDeleteComplaint() {
        // Mock the admin to have permissions to access complaints
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(complaintRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Void> response = complaintController.deleteComplaint(1, 1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(complaintRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteComplaintWithoutPermission() {
        // Mock the admin without permission to access complaints
        AdminEntity admin = mockAdminEntity(false);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(complaintRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Void> response = complaintController.deleteComplaint(1, 1);

        assertEquals(403, response.getStatusCodeValue()); // Forbidden
        verify(complaintRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteComplaintNotFound() {
        // Mock the admin to have permissions to access complaints
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(complaintRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Void> response = complaintController.deleteComplaint(1, 1);

        assertEquals(404, response.getStatusCodeValue()); // Not Found
        verify(complaintRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteUser() {
        // Mock the admin to have permissions to access users
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        when(clientRepository.existsById(1)).thenReturn(true);
        when(complaintRepository.findByClientEntityId(1)).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = complaintController.deleteUser(1, 1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("User and associated complaints deleted successfully.", response.getBody());
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUserWithoutPermission() {
        // Mock the admin without permission to access users
        AdminEntity admin = mockAdminEntity(false);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        ResponseEntity<String> response = complaintController.deleteUser(1, 1);

        assertEquals(403, response.getStatusCodeValue()); // Forbidden
        assertEquals(null, response.getBody());
        verify(clientRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteUserNotFound() {
        // Mock the admin to have permissions to access users
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        when(clientRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = complaintController.deleteUser(1, 1);

        assertEquals(404, response.getStatusCodeValue()); // Not Found
        assertEquals("User not found.", response.getBody());
        verify(clientRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteTechnician() {
        // Mock the admin to have permissions to access technicians
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        when(technicianRepository.existsById(1)).thenReturn(true);
        when(complaintRepository.findByTechnicianEntityId(1)).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = complaintController.deleteTechnician(1, 1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Technician and associated complaints deleted successfully.", response.getBody());
        verify(technicianRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTechnicianWithoutPermission() {
        // Mock the admin without permission to access technicians
        AdminEntity admin = mockAdminEntity(false);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        ResponseEntity<String> response = complaintController.deleteTechnician(1, 1);

        assertEquals(403, response.getStatusCodeValue()); // Forbidden
        assertEquals(null, response.getBody());
        verify(technicianRepository, never()).deleteById(1);
    }

    @Test
    void testDeleteTechnicianNotFound() {
        // Mock the admin to have permissions to access technicians
        AdminEntity admin = mockAdminEntity(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        when(technicianRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = complaintController.deleteTechnician(1, 1);

        assertEquals(404, response.getStatusCodeValue()); // Not Found
        assertEquals("Technician not found.", response.getBody());
        verify(technicianRepository, never()).deleteById(1);
    }

    @Test
    void testGetClientAndTechnicianNames() {
        ComplaintEntity complaint = new ComplaintEntity();
        ClientEntity client = new ClientEntity();
        client.setFirstName("John");
        client.setLastName("Doe");
        complaint.setClientEntity(client);

        TechnicianEntity technician = new TechnicianEntity();
        technician.setFirstName("Jane");
        technician.setLastName("Smith");
        complaint.setTechnicianEntity(technician);

        when(complaintRepository.findById(1)).thenReturn(Optional.of(complaint));

        ResponseEntity<Map<String, String>> response = complaintController.getClientAndTechnicianNames(1);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().get("Client"));
        assertEquals("Jane Smith", response.getBody().get("Technician"));
    }
}
