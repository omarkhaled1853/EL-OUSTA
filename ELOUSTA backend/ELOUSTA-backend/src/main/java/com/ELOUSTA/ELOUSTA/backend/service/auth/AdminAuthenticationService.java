package com.ELOUSTA.ELOUSTA.backend.service.auth;

import com.ELOUSTA.ELOUSTA.backend.Enums.ValidationStatus;
import com.ELOUSTA.ELOUSTA.backend.dto.authDto.AdminAdditionDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import com.ELOUSTA.ELOUSTA.backend.service.admin.AdminService;
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
    @Autowired
    private AdminService adminService;

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

    public String addAdmin(AdminAdditionDTO dto, int adminId) {
        if(!this.validUsername(dto.getUsername())){
            return ValidationStatus.INVALID_USERNAME.getMessage();
        }
        if(!adminService.adminCanHireAdmin(adminId)){
            return ValidationStatus.FAIL.getMessage();
        }

        dto.setPassword(encoder.encode(dto.getPassword()));

        adminRepository.save(createAdmin(dto));

        return ValidationStatus.VALID.getMessage();
    }
    private boolean validUsername(String username){
        if(username == null || username.length() > 20){
            return false;
        }
        Optional<AdminEntity> adminEntity = adminRepository.findByUsername(username);
        return adminEntity.isEmpty();
    }

    private AdminEntity createAdmin(AdminAdditionDTO adminAdditionDTO){
        return AdminEntity
                .builder()
                .username(adminAdditionDTO.getUsername())
                .password(adminAdditionDTO.getPassword())
                .fixed(false)
                .canHireAdmins(adminAdditionDTO.isCanHireAdmins())
                .canAccessComplaints(adminAdditionDTO.isCanAccessComplaints())
                .canAccessTechnician(adminAdditionDTO.isCanAccessTechnician())
                .canAccessUsers(adminAdditionDTO.isCanAccessUsers())
                .canManipulateProfessions(adminAdditionDTO.isCanManipulateProfessions())
                .build();
    }

}
