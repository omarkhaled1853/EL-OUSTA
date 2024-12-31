package com.ELOUSTA.ELOUSTA.backend.service.AdminAddition;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAdditionService {

    @Autowired
    private AdminRepository repository;
    public boolean addAdmin(int id,AdminAdditionDTO dto) {

        AdminEntity adminEntity=this.repository.findById(id).get();

        if (!adminEntity.isCanHireAdmins())
            return false;
        else
        {
            //TODO: meedo code
            //TODO: Abdelraziq code

        }
        return true;

    }
}
