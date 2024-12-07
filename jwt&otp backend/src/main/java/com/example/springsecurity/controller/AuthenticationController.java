package com.example.springsecurity.controller;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.dto.AuthRequest;
import com.example.springsecurity.dto.GoogleAuthRequest;
import com.example.springsecurity.entity.TechnicianEntity;
import com.example.springsecurity.entity.ClientEntity;
import com.example.springsecurity.service.JwtService;
import com.example.springsecurity.service.TechnicianAuthenticationService;
import com.example.springsecurity.service.ClientAuthenticationService;
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