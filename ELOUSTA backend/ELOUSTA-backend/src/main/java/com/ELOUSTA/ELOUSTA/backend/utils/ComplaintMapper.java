package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;

public final class ComplaintMapper {

    public static ComplaintEntity complaintDTOToComplaintEntity(ComplaintDTO complaintDTO,
                                                                ClientEntity clientEntity,
                                                                TechnicianEntity technicianEntity,
                                                                boolean isClientToTechnician) {
        return ComplaintEntity.builder()
                .clientEntity(clientEntity)
                .technicianEntity(technicianEntity)
                .complaintBody(complaintDTO.getComplaintBody())
                .state("PENDING")
                .direction(isClientToTechnician ? 0 : 1)  // 0 if client to technician, 1 if technician to client
                .build();
    }

}
