package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;

public final class ComplaintMapper {

    public static ComplaintEntity complaintDTOToClientComplaintEntity(ComplaintDTO complaintDTO,
                                                                      ClientEntity clientEntity,
                                                                      TechnicianEntity technicianEntity) {
        return ComplaintEntity.builder()
                .clientEntity(clientEntity)
                .technicianEntity(technicianEntity)
                .complaintBody(complaintDTO.getComplaintBody())
                .state("PENDING")
                .direction(false)
                .build();
    }

    public static ComplaintEntity complaintDTOToTechnicinaComplaintEntity(ComplaintDTO complaintDTO,
                                                                          ClientEntity clientEntity,
                                                                          TechnicianEntity technicianEntity) {
        return ComplaintEntity.builder()
                .clientEntity(clientEntity)
                .technicianEntity(technicianEntity)
                .complaintBody(complaintDTO.getComplaintBody())
                .state("PENDING")
                .direction(true)
                .build();
    }
}
