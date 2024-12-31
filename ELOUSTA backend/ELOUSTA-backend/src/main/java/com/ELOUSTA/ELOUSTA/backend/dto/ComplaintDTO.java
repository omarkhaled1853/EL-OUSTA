package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ComplaintDTO {
    private int clientId;
    private int techId;
    private String complaintBody;
}
