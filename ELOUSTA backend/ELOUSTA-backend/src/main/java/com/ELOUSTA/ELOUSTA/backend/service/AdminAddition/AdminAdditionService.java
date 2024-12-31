package com.ELOUSTA.ELOUSTA.backend.service.AdminAddition;

import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.model.MailStructure;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import com.ELOUSTA.ELOUSTA.backend.service.otp.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAdditionService {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private MailService mailService;
    public boolean addAdmin(int id,AdminAdditionDTO dto) {


        AdminEntity adminEntity=this.repository.findById(id).get();

        if (!adminEntity.isCanHireAdmins())
            return false;
        else
       {
            //TODO: meedo code
           // TODO: Abdelraziq code
            MailStructure structure=new MailStructure();
            structure.setSubject("new admin");
            structure.setMessage("Congratulations");
            mailService.sendAdminMail(dto.getEmail(),structure,dto.getUsername(),dto.getPassword());
        }

        return true;

    }
}
