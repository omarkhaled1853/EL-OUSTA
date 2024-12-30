package com.ELOUSTA.ELOUSTA.backend.dto.profileDto;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.PortfolioDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@ToString(callSuper = true)
public class TechnicianProfileDTO extends UserProfileDTO {
    private Double rate;
    private int experience;
    private String description;
    private List<PortfolioDto> portfolioDto;
    private DomainDTO domainDTO;
}
