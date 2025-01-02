package com.ELOUSTA.ELOUSTA.backend.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials {
    private String status;
    private String token;
    private String id;
}
