package com.example.springsecurity.service;

import com.example.springsecurity.Enums.ValidationStatus;
import com.example.springsecurity.entity.Technician;
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


    public String addTechnician(Technician technician){
        String validateState = validateTechnician(technician);
        if(!validateState.equals(ValidationStatus.VALID.getMessage())){
            return validateState;
        }

        technician.setPassword(encoder.encode(technician.getPassword()));
        technicianRepository.save(technician);

        return ValidationStatus.VALID.getMessage();
    }
    private String validateTechnician(Technician technician){
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
}
