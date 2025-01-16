package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/client")
public class ClientAuthentication {
    @Autowired
    private ClientAuthenticationService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signUp")
    public String addNewUser(@RequestBody ClientEntity clientEntity){
        String validationStatus = userService.validateUniqueUsernameAndEmail(clientEntity);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return userService.addUser(clientEntity);
        }
        return validationStatus;
    }

    @PostMapping("/signIn")
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
    @PostMapping("/signIn/google")
    public String googleAuthenticationUser(@RequestBody GoogleAuthRequest googleAuthRequest){
        Boolean isAuthenticated = userService.validAuthenticationWithGoogle(googleAuthRequest);

        if(isAuthenticated){
            String username = userService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress()).getUsername();
            return jwtService.generateToken(username);
        }
        return ValidationStatus.FAIL.getMessage();
    }
}