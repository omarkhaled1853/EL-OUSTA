package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private int clientId;
    private int techId;
    private String description;
    private String location;
    private String state;
    private Date startDate;
    private Date endDate;
}
