package com.ELOUSTA.ELOUSTA.backend.service.auth;


import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.GoogleAuthRequest;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.ResetPasswordRequest;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianAuthenticationService implements UserDetailsService {
    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TechnicianEntity> technician = technicianRepository.findByUsername(username);

        if(technician.isPresent()){
            return new TechnicianAuthenticationDetails(technician.get());
        }
        throw new UsernameNotFoundException("User not found");
    }
    public TechnicianEntity loadUserByUsernameAsTechnician(String username){
        Optional<TechnicianEntity> technician = technicianRepository.findByUsername(username);

        if(technician.isPresent()){
            return technician.get();
        }
        throw new IllegalArgumentException("Invalid username");
    }

    public String addTechnician(TechnicianEntity technicianEntity){
        technicianEntity.setPassword(encoder.encode(technicianEntity.getPassword()));
        technicianRepository.save(technicianEntity);

        return ValidationStatus.VALID.getMessage();
    }

    public String resetPassword(ResetPasswordRequest request){
        Optional<TechnicianEntity> technician = technicianRepository.findByUsername(request.getUsername());

        if(technician.isEmpty()){
            return ValidationStatus.FAIL.getMessage();
        }
        technician.get().setPassword(request.getPassword());
        return addTechnician(technician.get());
    }

    public String validateUniqueUsernameAndEmail(TechnicianEntity technicianEntity){
        if(!validUsername(technicianEntity.getUsername())){
            return ValidationStatus.INVALID_USERNAME.getMessage();
        }
        if(!validEmailAddress(technicianEntity.getEmailAddress())){
            return ValidationStatus.INVALID_EMAIL.getMessage();
        }
        return ValidationStatus.VALID.getMessage();
    }
    private Boolean validUsername(String username){
        if(username == null){
            return false;
        }
        Optional<TechnicianEntity> technician = technicianRepository.findByUsername(username);
        return technician.isEmpty();
    }
    private Boolean validEmailAddress(String emailAddress){
        if (emailAddress == null){
            return false;
        }
        Optional<TechnicianEntity> technician = technicianRepository.findByEmailAddress(emailAddress);
        return technician.isEmpty();
    }

    //In authentication with Google we just check if email exist
    public Boolean validAuthenticationWithGoogle(GoogleAuthRequest googleAuthRequest){
        Optional<TechnicianEntity> technician = technicianRepository.findByEmailAddress(googleAuthRequest.getEmailAddress());
        return technician.isPresent();
    }
    public TechnicianEntity loadUserByEmailAddress(String emailAddress){
        Optional<TechnicianEntity> technician = technicianRepository.findByEmailAddress(emailAddress);

        if(technician.isPresent()){
            return technician.get();
        }
        throw new IllegalArgumentException("Invalid emailAddress");
    }
}
