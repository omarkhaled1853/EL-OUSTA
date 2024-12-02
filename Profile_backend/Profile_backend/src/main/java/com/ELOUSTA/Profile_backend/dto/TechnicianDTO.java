package com.ELOUSTA.Profile_backend.dto;

import com.ELOUSTA.Profile_backend.entity.Domain;
import com.ELOUSTA.Profile_backend.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDTO extends UserDTO{
    private Double rate;
    private int experience;
    private List<Portfolio> portfolios;
    private Domain domain;
}
