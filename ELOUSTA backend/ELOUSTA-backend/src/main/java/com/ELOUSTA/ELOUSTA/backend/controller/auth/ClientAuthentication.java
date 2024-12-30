package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.Credentials;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/client")
public class ClientAuthentication {
    @Autowired
    @Qualifier("clientDetailsService")
    private ClientAuthenticationService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signUp")
    public String addNewUser(@RequestBody ClientEntity clientEntity){
        String validationStatus = userService.validateUniqueUsernameAndEmail(clientEntity);
        if(validationStatus.equals(ValidationStatus.VALID.getMessage())){
            return userService.addUser(clientEntity); // return the Id of the new user
        }
        return validationStatus;
    }

    @PostMapping("/signIn")
    public Credentials authenticateAndGetTokenUser(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        Credentials credentials;
        if (authentication.isAuthenticated()) {
            int id = userService.loadUserByUsernameAsUserInfo(authRequest.getUsername()).getId();

            credentials = Credentials
                    .builder()
                    .token(jwtService.generateToken(authRequest.getUsername()))
                    .status(ValidationStatus.VALID.getMessage())
                    .id(String.valueOf(id))
                    .build();
        } else {
            credentials = Credentials
                    .builder().
                    status(ValidationStatus.FAIL.getMessage()).
                    build();
        }

        return credentials;
    }
    @PostMapping("/signIn/google")
    public Credentials googleAuthenticationUser(@RequestBody GoogleAuthRequest googleAuthRequest){
        Boolean isAuthenticated = userService.validAuthenticationWithGoogle(googleAuthRequest);

        Credentials credentials;
        if (isAuthenticated) {
            ClientEntity client = userService.loadUserByEmailAddress(googleAuthRequest.getEmailAddress());

            credentials = Credentials
                    .builder()
                    .token(jwtService.generateToken(client.getUsername()))
                    .status(ValidationStatus.VALID.getMessage())
                    .id(String.valueOf(client.getId()))
                    .build();
        } else {
            credentials = Credentials
                    .builder().
                    status(ValidationStatus.FAIL.getMessage()).
                    build();
        }

        return credentials;
    }
}