package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtServiceTest {
    private JwtService jwtService;
    private UserDetails mockUserDetails;

    @BeforeAll
    void setup(){
        jwtService = new JwtService();
        mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("Test username");
    }

    @Test
    void generateTokenTest(){
        String token = jwtService.generateToken("mohamed");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsernameTest(){
        String username = "mohamed ahmed";

        String token = jwtService.generateToken(username);
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void expireDateWithingValidRange(){ // check that expire date is withing 24 hour
        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken("mohamed");
        Date date = jwtService.extractExpiration(token);
        assertTrue(date.before(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000))));
    }

    @Test
    void validateTokenTest(){
        String token = jwtService.generateToken("mohamed");
        when(mockUserDetails.getUsername()).thenReturn("mohamed");

        assertTrue(jwtService.validateToken(token, mockUserDetails));
    }

}