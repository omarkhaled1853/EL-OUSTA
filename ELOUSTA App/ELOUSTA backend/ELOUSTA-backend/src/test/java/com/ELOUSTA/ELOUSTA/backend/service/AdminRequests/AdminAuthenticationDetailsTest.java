package com.ELOUSTA.ELOUSTA.backend.service.AdminRequests;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.AdminAuthenticationDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAuthenticationDetailsTest {

    @Mock
    private AdminEntity adminEntity;

    private AdminAuthenticationDetails adminAuthenticationDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize adminEntity mock
        when(adminEntity.getUsername()).thenReturn("adminUsername");
        when(adminEntity.getPassword()).thenReturn("adminPassword");

        // Create an instance of AdminAuthenticationDetails
        adminAuthenticationDetails = new AdminAuthenticationDetails(adminEntity);
    }

    @Test
    public void testGetUsername() {
        assertEquals("adminUsername", adminAuthenticationDetails.getUsername());
        verify(adminEntity, times(1)).getUsername();
    }

    @Test
    public void testGetPassword() {
        assertEquals("adminPassword", adminAuthenticationDetails.getPassword());
        verify(adminEntity, times(1)).getPassword();
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(adminAuthenticationDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(adminAuthenticationDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(adminAuthenticationDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(adminAuthenticationDetails.isEnabled());
    }

    @Test
    public void testGetAuthorities() {
        assertNull(adminAuthenticationDetails.getAuthorities());
    }
}
