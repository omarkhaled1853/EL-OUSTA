package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestsDTO {

    private String technicianUserName;
    private String clientUserName;
    private Date startDate;
    private Date endDate;
}
