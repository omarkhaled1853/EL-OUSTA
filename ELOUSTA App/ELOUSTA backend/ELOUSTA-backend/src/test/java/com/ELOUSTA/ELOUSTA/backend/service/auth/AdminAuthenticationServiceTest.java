package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AdminAdditionDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import com.ELOUSTA.ELOUSTA.backend.service.admin.AdminService;
import com.ELOUSTA.ELOUSTA.backend.service.otp.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminAuthenticationServiceTest {
    @InjectMocks
    private AdminAuthenticationService adminAuthenticationService;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AdminService adminService;
    @Mock
    private MailService mailService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testLoadUserByUsername_userNotExist(){
        String username = "admin";
        when(adminRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> adminAuthenticationService.loadUserByUsername(username));
    }
    @Test
    void testLoadUserByUsername_userExist(){
        String username = "admin";
        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.findByUsername(username)).thenReturn(Optional.of(adminEntity));

        UserDetails userDetails = new AdminAuthenticationDetails(adminEntity);
        assertNotNull(userDetails);
    }

    @Test
    void testLoadAdminBuUsername_adminExist(){
        String username = "admin";
        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.findByUsername(username)).thenReturn(Optional.of(adminEntity));

        AdminEntity result = adminAuthenticationService.loadAdminByUsername(username);
        assertEquals(adminEntity, result);
    }
    @Test
    void testLoadAdminBuUsername_adminNotExist(){
        String username = "admin";
        when(adminRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> adminAuthenticationService.loadAdminByUsername(username));
    }

    @Test
    void testAddAdmin_validUsername() {
        AdminAdditionDTO dto = new AdminAdditionDTO();
        dto.setUsername("newAdmin");
        dto.setPassword("password");
        dto.setEmail("admin@example.com");
        dto.setCanHireAdmins(true);

        int adminId = 1;
        AdminEntity adminEntity = new AdminEntity(); // to mock admin entity

        when(adminService.adminCanHireAdmin(adminId)).thenReturn(true);
        when(adminRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        String result = adminAuthenticationService.addAdmin(dto, adminId);


        assertEquals("valid", result);
    }
    @Test
    void testAddAdmin_exceedSizeOfUserName() {
        AdminAdditionDTO dto = new AdminAdditionDTO();
        dto.setUsername("newAdminnewAdminnewAdminnewAdminnewAdminnewAdminnewAdminnewAdmin");
        dto.setPassword("password");
        dto.setEmail("admin@example.com");
        dto.setCanHireAdmins(true);

        int adminId = 1;
        AdminEntity adminEntity = new AdminEntity(); // to mock admin entity

        when(adminService.adminCanHireAdmin(adminId)).thenReturn(true);
        when(adminRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        String result = adminAuthenticationService.addAdmin(dto, adminId);


        assertEquals("Invalid username", result);
    }
    @Test
    void testAddAdmin_AdminCannotAddAdmin() {
        AdminAdditionDTO dto = new AdminAdditionDTO();
        dto.setUsername("newAdmin");
        dto.setPassword("password");
        dto.setEmail("admin@example.com");
        dto.setCanHireAdmins(true);

        int adminId = 1;
        AdminEntity adminEntity = new AdminEntity(); // to mock admin entity

        when(adminService.adminCanHireAdmin(adminId)).thenReturn(false);
        when(adminRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(encoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        String result = adminAuthenticationService.addAdmin(dto, adminId);


        assertEquals("fail", result);
    }
}