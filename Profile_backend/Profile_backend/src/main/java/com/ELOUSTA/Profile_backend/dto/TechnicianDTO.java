package com.ELOUSTA.Profile_backend.dto;

import com.ELOUSTA.Profile_backend.entity.DomainEntity;
import com.ELOUSTA.Profile_backend.entity.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class TechnicianDTO extends UserDTO{
    private Double rate;
    private int experience;
    private List<PortfolioEntity> portfolioEntities;
    private DomainEntity domainEntity;
}
