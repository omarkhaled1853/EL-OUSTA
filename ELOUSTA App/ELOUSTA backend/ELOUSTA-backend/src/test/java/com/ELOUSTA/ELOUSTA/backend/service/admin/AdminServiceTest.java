package com.ELOUSTA.ELOUSTA.backend.service.admin;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;
    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdminCanDeleteClient_canDelete(){
        AdminEntity adminEntity = AdminEntity
                .builder()
                .canAccessUsers(true)
                .build();

        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

        boolean result = adminService.adminCanDeleteClient(adminId);
        assertTrue(result);
    }

    @Test
    void testAdminCanDeleteClient_canNotDelete(){
        AdminEntity adminEntity = AdminEntity
                .builder()
                .canAccessUsers(false)
                .build();

        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

        boolean result = adminService.adminCanDeleteClient(adminId);
        assertFalse(result);
    }

    @Test
    void testAdminCanDeleteClient_adminNotExist(){
        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        boolean result = adminService.adminCanDeleteClient(adminId);

        assertFalse(result);
    }


    @Test
    void testAdminCanHireAdmin_canHire(){
        AdminEntity adminEntity = AdminEntity
                .builder()
                .canHireAdmins(true)
                .build();

        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

        boolean result = adminService.adminCanHireAdmin(adminId);
        assertTrue(result);
    }

    @Test
    void testAdminCanHireClient_canNotHire(){
        AdminEntity adminEntity = AdminEntity
                .builder()
                .canHireAdmins(false)
                .build();

        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(adminEntity));

        boolean result = adminService.adminCanHireAdmin(adminId);
        assertFalse(result);
    }

    @Test
    void testAdminCanHireAdmin_adminNotExist(){
        int adminId = 1;

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        boolean result = adminService.adminCanHireAdmin(adminId);

        assertFalse(result);
    }

}