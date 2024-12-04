package com.example.Backend.classes;

import com.example.Backend.entities.technicianEntity;
import org.springframework.stereotype.Service;

@Service
public class technicianMapper {

    public TechnicianDTO technicainToTechnicianDTO(Technician technician)
    {
        TechnicianDTO dto=new TechnicianDTO.TechnicianDTOBuilder()
                .id(technician.getId())
                .domain(technician.getDomain())
                .fName(technician.getFName())
                .lName(technician.getLName())
                .governorate(technician.getGovernorate())
                .district(technician.getDistrict())
                .startDate(technician.getStartDate())
                .rate(technician.getRate())
                .build();
        return dto;
    }
    public Technician technicianEntityToTechnician(technicianEntity entity)
    {
        Technician technician= Technician.builder()
                .id(entity.getId())
                .userName(entity.getUsername())
                .domain(entity.getDomain())
                .emailAddress(entity.getEmailAddress())
                .fName(entity.getFName())
                .lName(entity.getLName())
                .dob(entity.getDob())
                .phoneNumber(entity.getPhoneNumber())
                .authProvider(entity.getAuthProvider())
                .governorate(entity.getGovernorate())
                .district(entity.getDistrict())
                .startDate(entity.getStartDate())
                .rate(entity.getRate())
                .build();
        return technician;
    }


}
