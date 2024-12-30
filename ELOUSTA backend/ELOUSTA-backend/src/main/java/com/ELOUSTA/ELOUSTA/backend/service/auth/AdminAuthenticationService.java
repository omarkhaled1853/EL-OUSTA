package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthenticationService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminEntity> adminEntity = adminRepository.findByUsername(username);

        if(adminEntity.isPresent()){
            return new AdminAuthenticationDetails(adminEntity.get());
        }

        throw new UsernameNotFoundException("No admin with name: " + username);
    }

    public AdminEntity loadAdminByUsername(String username){
        Optional<AdminEntity> adminEntity = adminRepository.findByUsername(username);

        if(adminEntity.isPresent()){
            return adminEntity.get();
        }

        throw new UsernameNotFoundException("No admin with name: " + username);
    }

    public String addAdmin(AdminEntity adminEntity) {
        adminEntity.setPassword(encoder.encode(adminEntity.getPassword()));
        adminRepository.save(adminEntity);

        return ValidationStatus.VALID.getMessage();
    }
}
