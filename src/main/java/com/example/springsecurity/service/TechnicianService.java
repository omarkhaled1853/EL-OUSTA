package com.example.springsecurity.service;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.GoogleAuthRequest;
import com.example.springsecurity.entity.Technician;
import com.example.springsecurity.entity.UserInfo;
import com.example.springsecurity.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianService implements UserDetailsService {
    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Technician> technician = technicianRepository.findByUsername(username);

        if(technician.isPresent()){
            return new TechnicianDetails(technician.get());
        }
        throw new UsernameNotFoundException("User not found");
    }
    public Technician loadUserByUsernameAsTechnician(String username){
        Optional<Technician> technician = technicianRepository.findByUsername(username);

        if(technician.isPresent()){
            return technician.get();
        }
        throw new IllegalArgumentException("Invalid username");
    }

    public String addTechnician(Technician technician){
        technician.setPassword(encoder.encode(technician.getPassword()));
        technicianRepository.save(technician);

        return ValidationStatus.VALID.getMessage();
    }
    public String validateUniqueUsernameAndEmail(Technician technician){
        if(!validUsername(technician.getUsername())){
            return ValidationStatus.INVALID_USERNAME.getMessage();
        }
        if(!validEmailAddress(technician.getEmailAddress())){
            return ValidationStatus.INVALID_EMAIL.getMessage();
        }
        return ValidationStatus.VALID.getMessage();
    }
    private Boolean validUsername(String username){
        if(username == null){
            return false;
        }
        Optional<Technician> technician = technicianRepository.findByUsername(username);
        return technician.isEmpty();
    }
    private Boolean validEmailAddress(String emailAddress){
        if (emailAddress == null){
            return false;
        }
        Optional<Technician> technician = technicianRepository.findByEmailAddress(emailAddress);
        return technician.isEmpty();
    }

    //In authentication with Google we just check if email exist
    public Boolean validAuthenticationWithGoogle(GoogleAuthRequest googleAuthRequest){
        Optional<Technician> technician = technicianRepository.findByEmailAddress(googleAuthRequest.getEmailAddress());
        return technician.isPresent();
    }
    public Technician loadUserByEmailAddress(String emailAddress){
        Optional<Technician> technician = technicianRepository.findByEmailAddress(emailAddress);

        if(technician.isPresent()){
            return technician.get();
        }
        throw new IllegalArgumentException("Invalid emailAddress");
    }
}
