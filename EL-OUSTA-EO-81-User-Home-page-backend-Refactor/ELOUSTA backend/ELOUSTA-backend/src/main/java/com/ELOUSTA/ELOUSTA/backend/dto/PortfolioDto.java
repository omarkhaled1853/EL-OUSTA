package com.ELOUSTA.ELOUSTA.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class PortfolioDto {
    private Integer id;
    private byte[] photo;
}