package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private ClientAuthenticationService userService;

    @Autowired
    private TechnicianAuthenticationService technicianAuthenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/signUp")
    public String addNewUser(@RequestBody ClientEntity clientEntity){
        String validationStatus = userService.validateUniqueUsernameAndEmail(clientEntity);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return userService.addUser(clientEntity);
        }
        return validationStatus;
    }

    @PostMapping("/tech/signUp")
    public String addNewTech(@RequestBody TechnicianEntity technicianEntity){
        String validationStatus = technicianAuthenticationService.validateUniqueUsernameAndEmail(technicianEntity);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return technicianAuthenticationService.addTechnician(technicianEntity);
        }
        return validationStatus;
    }

    @PostMapping("/user/signIn")
    public String authenticateAndGetTokenUser(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            return ValidationStatus.FAIL.getMessage();
        }
    }

    @PostMapping("/tech/signIn")
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

    @PostMapping("/user/signIn/google")
    public String googleAuthenticationUser(@RequestBody GoogleAuthRequest googleAuthRequest){
         Boolean isAuthenticated = userService.validAuthenticationWithGoogle(googleAuthRequest);

        if(isAuthenticated){
            String username = userService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress()).getUsername();
            return jwtService.generateToken(username);
        }
        return ValidationStatus.FAIL.getMessage();
    }
    @PostMapping("/tech/signIn/google")
    public String googleAuthenticationTech(@RequestBody GoogleAuthRequest googleAuthRequest){
         Boolean isAuthenticated = technicianAuthenticationService.validAuthenticationWithGoogle(googleAuthRequest);

        if(isAuthenticated){
            String username = technicianAuthenticationService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress()).getUsername();
            return jwtService.generateToken(username);
        }
        return ValidationStatus.FAIL.getMessage();
    }
}