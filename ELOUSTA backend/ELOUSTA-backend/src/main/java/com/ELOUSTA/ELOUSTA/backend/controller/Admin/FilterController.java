package com.ELOUSTA.ELOUSTA.backend.controller.Admin;

import com.ELOUSTA.ELOUSTA.backend.dto.ProfessionCardDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    private RequestService requestService;

    @GetMapping("/Complains_increasing")
    public List<ProfessionCardDTO> getTechniciansincreasing() {
        List<ProfessionCardDTO> result = new ArrayList<>();
        List<TechnicianEntity> techs = requestService.getprofessiontech();

        for (TechnicianEntity p : techs) {
            ProfessionCardDTO cardDTO = new ProfessionCardDTO();
            cardDTO.setTechId(p.getId());
            cardDTO.setTechName(p.getFirstName());
            cardDTO.setEmail(p.getEmailAddress());
            cardDTO.setRate(p.getRate());
//            cardDTO.setComplainNumber(p.complainservice.getcount(p.getId()));  // Placeholder for actual complain count
            cardDTO.setProfessionName(p.getDomainEntity().getName());
            result.add(cardDTO);
        }

        // Sort the result list based on the complain number in increasing order
        result.sort(Comparator.comparingInt(ProfessionCardDTO::getComplainNumber));

        return result;
    }

    @GetMapping("/Complains_decreasing")
    public List<ProfessionCardDTO> getTechniciansdecreasing() {
        List<ProfessionCardDTO> result = new ArrayList<>();
        List<TechnicianEntity> techs = requestService.getprofessiontech();

        for (TechnicianEntity p : techs) {
            ProfessionCardDTO cardDTO = new ProfessionCardDTO();
            cardDTO.setTechId(p.getId());
            cardDTO.setTechName(p.getFirstName());
            cardDTO.setEmail(p.getEmailAddress());
            cardDTO.setRate(p.getRate());
//            cardDTO.setComplainNumber(p.complainservice.getcount(p.getId()));  // Placeholder for actual complain count
            cardDTO.setProfessionName(p.getDomainEntity().getName());
            result.add(cardDTO);
        }

        // Sort the result list based on the complain number in decreasing order
        result.sort(Comparator.comparingInt(ProfessionCardDTO::getComplainNumber).reversed());

        return result;
    }

}
