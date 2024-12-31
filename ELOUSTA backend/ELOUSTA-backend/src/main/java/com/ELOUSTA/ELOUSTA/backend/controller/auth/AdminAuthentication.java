package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AdminAdditionDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.Credentials;
import com.ELOUSTA.ELOUSTA.backend.service.auth.AdminAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminAuthentication {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    public Credentials authenticationAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));


        Credentials credentials;
        if(authentication.isAuthenticated()){
            int id = adminAuthenticationService.loadAdminByUsername(authRequest.getUsername()).getId();

            credentials = Credentials
                    .builder()
                    .token(jwtService.generateToken(authRequest.getUsername()))
                    .status(ValidationStatus.VALID.getMessage())
                    .id(String.valueOf(id))
                    .build();
        }else{
            credentials = Credentials
                    .builder()
                    .status(ValidationStatus.FAIL.getMessage())
                    .build();
        }
        return credentials;
    }
    @PostMapping("/register")
    public boolean addAdmin(@RequestBody AdminAdditionDTO adminAdditionDTO, @RequestParam int adminId){
        return adminAuthenticationService.addAdmin(adminAdditionDTO, adminId).equals(ValidationStatus.VALID.getMessage());
    }

}
