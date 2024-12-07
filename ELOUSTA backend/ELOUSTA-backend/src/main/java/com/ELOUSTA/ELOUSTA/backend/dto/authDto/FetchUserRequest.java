package com.ELOUSTA.ELOUSTA.backend.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchUserRequest {
    private String username;
}
