package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class ViewRequestDTO {
    private int id;
    private int techId;
    private int clientId;
    private String state;
    private String description;
    private String location;
    private Date startDate;
    private Date endDate;
}
