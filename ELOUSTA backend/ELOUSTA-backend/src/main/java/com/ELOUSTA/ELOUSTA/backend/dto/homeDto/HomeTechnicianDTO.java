package com.ELOUSTA.ELOUSTA.backend.dto.homeDto;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeTechnicianDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String city;
    private DomainDTO domainDTO;
    private int experience;    //TODO : Calculate experience
    private Double rate;
}
