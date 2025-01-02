package com.ELOUSTA.ELOUSTA.backend.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequestCountDTO {
    private int id;
    private String emailAddress;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long cnt;
}
