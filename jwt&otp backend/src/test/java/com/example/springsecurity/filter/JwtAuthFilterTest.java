package com.example.springsecurity.filter;

import com.example.springsecurity.service.JwtService;
import com.example.springsecurity.service.TechnicianAuthenticationService;
import com.example.springsecurity.service.ClientAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private ClientAuthenticationService clientAuthenticationService;

    @Mock
    private TechnicianAuthenticationService technicianAuthenticationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithValidUserToken() throws ServletException, IOException {
        String token = "validToken";
        String username = "user";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(request.getRequestURI()).thenReturn("/user");
        when(clientAuthenticationService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(clientAuthenticationService).loadUserByUsername(username);
        verify(jwtService).validateToken(token, userDetails);
        verify(filterChain).doFilter(request, response);

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        assert auth.getPrincipal().equals(userDetails);
    }

    @Test
    void testDoFilterInternal_WithValidTechToken() throws ServletException, IOException {
        String token = "validToken";
        String username = "tech";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(request.getRequestURI()).thenReturn("/tech");
        when(technicianAuthenticationService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(technicianAuthenticationService).loadUserByUsername(username);
        verify(jwtService).validateToken(token, userDetails);
        verify(filterChain).doFilter(request, response);

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        assert auth.getPrincipal().equals(userDetails);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws ServletException, IOException {
        String token = "invalidToken";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(token)).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).validateToken(anyString(), any(UserDetails.class));
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService, never()).extractUsername(anyString());
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
