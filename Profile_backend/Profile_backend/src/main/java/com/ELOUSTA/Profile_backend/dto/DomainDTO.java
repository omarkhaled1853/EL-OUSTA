package com.ELOUSTA.Profile_backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Builder
@Data
@ToString
public class DomainDTO {
    private Integer id;
    private String name;
    private String photo;
}
