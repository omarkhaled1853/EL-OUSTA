package com.ELOUSTA.ELOUSTA.backend.controller.Admin;

import com.ELOUSTA.ELOUSTA.backend.dto.DialogDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.ProfessionCardDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/profession")
public class ProfessionsController {
    @Autowired
    RequestService requestService;
    @Autowired
    AdminRepository adminRepository;
    @PostMapping("/addprofession/{adminId}")
    public String addprofession(@RequestBody DomainEntity domainEntity , @PathVariable int adminId)
    {
        if(requestService.canDomanipulatprofession(adminId)){
        requestService.saveProfession(domainEntity);
        return "Adding profession is successfully";}
        else  return null;
    }
    @GetMapping("/get_techs")
    public List<ProfessionCardDTO> getTechnicians() {
        List<ProfessionCardDTO> result = new ArrayList<>();
        List<TechnicianEntity> techs = requestService.getprofessiontech();
        for (TechnicianEntity p : techs) {
            ProfessionCardDTO cardDTO = new ProfessionCardDTO();
            cardDTO.setTechId(p.getId());
            cardDTO.setTechName(p.getFirstName());
            cardDTO.setEmail(p.getEmailAddress());
            cardDTO.setRate(p.getRate());
//            cardDTO.setComplainNumber(complaintRepository.findById(p.getId()).size);  // Placeholder for actual complain count
            cardDTO.setProfessionName(p.getDomainEntity().getName());
            result.add(cardDTO);
        }
        System.out.println(result.toString());
        return result;
    }
    @GetMapping("/get_dialog/{id}")
    public DialogDTO getdetailsdialog(@PathVariable int id) {
        DialogDTO result = new DialogDTO();
        List<TechnicianEntity> techs = requestService.getprofessiontech();
        for (TechnicianEntity p : techs) {
            if(p.getId() == id) {
                DialogDTO dialogDTO = new DialogDTO();
                dialogDTO.setTechName(p.getFirstName() + " " + p.getLastName());
                dialogDTO.setEmailAddress(p.getEmailAddress());
                dialogDTO.setProfession(p.getDomainEntity().getName());
                dialogDTO.setCity(p.getCity());
                dialogDTO.setPhoneNumber(p.getPhoneNumber());
                dialogDTO.setRate(p.getRate());
                dialogDTO.setSignUpDate(p.getSignUpDate());
               return dialogDTO;

            }
        }
        return result;
    }
    @DeleteMapping("/deletetech/{id}/{adminid}")
    public boolean removetech(@PathVariable int id,@PathVariable int adminid) {
        if (requestService.canDodelete(adminid)) {
            System.out.println(adminid + "deleeeeeeeeeeeeeete");
            requestService.deletetech(id);
            return true;
        }
        return false;
    }
}
