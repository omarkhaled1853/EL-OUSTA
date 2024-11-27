package com.example.springsecurity.service;

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
        technician.setPassword(encoder.encode(technician.getPassword()));
        technicianRepository.save(technician);

        return "Add technician successfully";
    }
}
