package com.ELOUSTA.ELOUSTA.backend.service.auth.filter;


//import com.ELOUSTA.ELOUSTA.backend.service.auth.AdminAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.AdminAuthenticationService;

import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ClientAuthenticationService userService;

    @Autowired
    private TechnicianAuthenticationService technicianAuthenticationService;


    @Autowired
    private AdminAuthenticationService adminAuthenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails;

            String requestURI = request.getRequestURI();
            if(requestURI.startsWith("/client")){
                userDetails = userService.loadUserByUsername(username);
            } else if (requestURI.startsWith("/tech")) {
                userDetails = technicianAuthenticationService.loadUserByUsername(username);

            } else if(requestURI.startsWith("/admin")){
                userDetails = adminAuthenticationService.loadUserByUsername(username);
            }
            else{

                throw new IllegalArgumentException();
            }

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
