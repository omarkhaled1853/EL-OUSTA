package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminComplaintDTO {
    private int id;
    private String complaintBody;
    private String state;
    private int direction;
    private int clientId;
    private String clientName;
    private int technicianId;
    private String technicianName;
}
