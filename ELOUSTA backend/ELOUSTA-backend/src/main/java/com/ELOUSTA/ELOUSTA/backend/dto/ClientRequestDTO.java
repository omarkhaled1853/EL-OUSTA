package com.ELOUSTA.ELOUSTA.backend.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class ClientRequestDTO {
    private int id;
    private int techId;
    private String state;
    private String description;
    private String Location;
    private Date startDate;
    private Date endDate;
}
