package com.ELOUSTA.ELOUSTA.backend.controller.Admin;

import com.ELOUSTA.ELOUSTA.backend.dto.DialogDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.DomainAdditionDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.ProfessionCardDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.*;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/profession")
public class ProfessionsController {
    @Autowired
    RequestService requestService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ComplaintRepository complaintRepository;
    @PostMapping("/addprofession/{adminId}")
    public String addprofession(@RequestBody DomainAdditionDTO domainAdditionDTO, @PathVariable int adminId)
    {

        if(requestService.canDomanipulatprofession(adminId)){
            System.out.println("adddding");
            DomainEntity domainEntity = new DomainEntity();
            domainEntity.setName(domainAdditionDTO.getName());
            domainEntity.setPhoto(domainAdditionDTO.getPhoto());
            requestService.saveProfession(domainEntity);
            return "Adding profession is successfully";
        }
        return null;
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
            cardDTO.setComplainNumber(complaintRepository.findByTechnicianEntity_Id(p.getId()).size());  // Placeholder for actual complain count
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
    @DeleteMapping("/deletetech/{id}/{adminId}")
    public boolean removeTech(@PathVariable int id,@PathVariable int adminId) {
        if (requestService.canDodelete(adminId)) {
            System.out.println(adminId + "deleeeeeeeeeeeeeete");
            requestService.deletetech(id);

            return true;
        }
        return false;
    }
}
