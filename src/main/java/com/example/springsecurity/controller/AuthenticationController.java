package com.example.springsecurity.controller;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.AuthRequest;
import com.example.springsecurity.entity.GoogleAuthRequest;
import com.example.springsecurity.entity.Technician;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.service.JwtService;
import com.example.springsecurity.service.TechnicianService;
import com.example.springsecurity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    private UserInfoService userService;

    @Autowired
    private TechnicianService technicianService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/signUp")
    public String addNewUser(@RequestBody UserInfo userInfo){
        String validationStatus = userService.validateUniqueUsernameAndEmail(userInfo);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return userService.addUser(userInfo);
        }
        return validationStatus;
    }

    @PostMapping("/tech/signUp")
    public String addNewTech(@RequestBody Technician technician){
        String validationStatus = technicianService.validateUniqueUsernameAndEmail(technician);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return technicianService.addTechnician(technician);
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
         Boolean isAuthenticated = technicianService.validAuthenticationWithGoogle(googleAuthRequest);

        if(isAuthenticated){
            String username = technicianService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress()).getUsername();
            return jwtService.generateToken(username);
        }
        return ValidationStatus.FAIL.getMessage();
    }
}