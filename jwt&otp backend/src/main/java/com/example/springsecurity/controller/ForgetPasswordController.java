package com.example.springsecurity.controller;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.dto.FetchUserRequest;
import com.example.springsecurity.dto.ResetPasswordRequest;
import com.example.springsecurity.entity.TechnicianEntity;
import com.example.springsecurity.entity.ClientEntity;
import com.example.springsecurity.service.JwtService;
import com.example.springsecurity.service.TechnicianAuthenticationService;
import com.example.springsecurity.service.ClientAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class ForgetPasswordController {

    @Autowired
    private ClientAuthenticationService clientAuthenticationService;
    @Autowired
    private TechnicianAuthenticationService technicianAuthenticationService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/user/fetchUser")
    public ClientEntity fetchUser(@RequestBody FetchUserRequest request){
        return clientAuthenticationService.loadUserByUsernameAsUserInfo(request.getUsername());
    }

    @PostMapping("/tech/fetchTch")
    public TechnicianEntity fetchTechnician(@RequestBody FetchUserRequest request){
        return technicianAuthenticationService.loadUserByUsernameAsTechnician(request.getUsername());
    }
    @PostMapping("/user/resetPassword")
    public String userResetPassword(@RequestBody ResetPasswordRequest request){
        String resetPasswordStatus = clientAuthenticationService.resetPassword(request);
        if (resetPasswordStatus.equals(ValidationStatus.VALID.getMessage())) {
            return jwtService.generateToken(request.getUsername());
        }
        return resetPasswordStatus; // fail (there is no user with the provided username)
    }
    @PostMapping("/tech/resetPassword")
    public String techResetPassword(@RequestBody ResetPasswordRequest request){
        String resetPasswordStatus = technicianAuthenticationService.resetPassword(request);
        if(resetPasswordStatus.equals(ValidationStatus.VALID.getMessage())){
            return jwtService.generateToken(request.getUsername());
        }
        return resetPasswordStatus; // fail (there is no user with the provided username)
    }
}
