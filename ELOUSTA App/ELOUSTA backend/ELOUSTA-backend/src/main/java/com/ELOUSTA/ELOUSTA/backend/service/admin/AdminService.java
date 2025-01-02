package com.ELOUSTA.ELOUSTA.backend.service.admin;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public boolean adminCanDeleteClient(int adminId){
        Optional<AdminEntity> adminEntity = adminRepository.findById(adminId);

        if(adminEntity.isEmpty()){
            return false;
        }
        return adminEntity.get().isCanAccessUsers();
    }

    public boolean adminCanHireAdmin(int adminId){
        Optional<AdminEntity> adminEntity = adminRepository.findById(adminId);

        if(adminEntity.isEmpty()){
            return false;
        }
        return adminEntity.get().isCanHireAdmins();
    }
}
