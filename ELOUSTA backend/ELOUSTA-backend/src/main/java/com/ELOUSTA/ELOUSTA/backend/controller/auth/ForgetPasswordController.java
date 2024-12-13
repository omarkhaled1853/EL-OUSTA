package com.ELOUSTA.ELOUSTA.backend.controller.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.FetchUserRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.ResetPasswordRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.auth.ClientAuthenticationService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.JwtService;
import com.ELOUSTA.ELOUSTA.backend.service.auth.TechnicianAuthenticationService;
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


    @PostMapping("/client/fetchUser")
    public ClientEntity fetchUser(@RequestBody FetchUserRequest request){
        return clientAuthenticationService.loadUserByUsernameAsUserInfo(request.getUsername());
    }

    @PostMapping("/tech/fetchTch")
    public TechnicianEntity fetchTechnician(@RequestBody FetchUserRequest request){
        return technicianAuthenticationService.loadUserByUsernameAsTechnician(request.getUsername());
    }
    @PostMapping("/client/resetPassword")
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
