package com.ELOUSTA.ELOUSTA.backend.service.home.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HomePayload {
    private String field;
    private String query;
}
