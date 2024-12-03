package com.ELOUSTA.Profile_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class DomainDTO {
    private Integer id;
    private String name;
    private byte[] photo;
}
