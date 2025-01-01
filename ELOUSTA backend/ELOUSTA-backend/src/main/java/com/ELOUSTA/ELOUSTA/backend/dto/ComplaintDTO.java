package com.ELOUSTA.ELOUSTA.backend.dto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintDTO {
    private int clientId;
    private int techId;
    private String complaintBody;
}
