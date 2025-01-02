package com.ELOUSTA.ELOUSTA.backend.service.request.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPayload {
    private int id;
    private String state;
    private String query;
}
