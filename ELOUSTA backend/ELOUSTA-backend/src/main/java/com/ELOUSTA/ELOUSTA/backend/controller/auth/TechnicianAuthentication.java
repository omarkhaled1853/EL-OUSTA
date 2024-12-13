package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tech")
public class TechnicianAuthentication {
    @Autowired
    private TechnicianAuthenticationService technicianAuthenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signUp")
    public String addNewTech(@RequestBody TechnicianEntity technicianEntity){
        String validationStatus = technicianAuthenticationService.validateUniqueUsernameAndEmail(technicianEntity);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return technicianAuthenticationService.addTechnician(technicianEntity);
        }
        return validationStatus;
    }

    @PostMapping("/signIn")
    public String authenticateAndGetTokenTechnician(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            return ValidationStatus.FAIL.getMessage();
        }
    }
    @PostMapping("/signIn/google")
    public String googleAuthenticationTech(@RequestBody GoogleAuthRequest googleAuthRequest){
         Boolean isAuthenticated = technicianAuthenticationService.validAuthenticationWithGoogle(googleAuthRequest);

        if(isAuthenticated){
            String username = technicianAuthenticationService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress()).getUsername();
            return jwtService.generateToken(username);
        }
        return ValidationStatus.FAIL.getMessage();
    }
}