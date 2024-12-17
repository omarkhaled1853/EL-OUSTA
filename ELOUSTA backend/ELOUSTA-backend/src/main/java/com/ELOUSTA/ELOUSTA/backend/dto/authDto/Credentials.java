package com.ELOUSTA.ELOUSTA.backend.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    private String token;
    private String id;
}
