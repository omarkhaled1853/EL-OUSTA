package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@ToString(callSuper = true)
public class TechnicianDTO extends UserDTO{
    private Double rate;
    private int experience;
    private String description;
    private List<PortfolioDto> portfolioDto;
    private DomainDTO domainDTO;
}
