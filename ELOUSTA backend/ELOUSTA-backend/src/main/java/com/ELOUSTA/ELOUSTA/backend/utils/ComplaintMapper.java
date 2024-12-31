package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;

public final class ComplaintMapper {

    public static ComplaintEntity complaintDTOToClientComplaintEntity(ComplaintDTO complaintDTO) {
        return ComplaintEntity.builder()
                .clientId(complaintDTO.getClientId())
                .techId(complaintDTO.getTechId())
                .complaintBody(complaintDTO.getComplaintBody())
                .state("PENDING")
                .direction(false)
                .build();
    }

    public static ComplaintEntity complaintDTOToTechnicinaComplaintEntity(ComplaintDTO complaintDTO) {
        return ComplaintEntity.builder()
                .clientId(complaintDTO.getClientId())
                .techId(complaintDTO.getTechId())
                .complaintBody(complaintDTO.getComplaintBody())
                .state("PENDING")
                .direction(true)
                .build();
    }
}
