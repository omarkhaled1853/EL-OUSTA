package com.ELOUSTA.ELOUSTA.backend.service.request.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestStatusPayload {

    private int id;
    private int clientId;
    private int techId;
}
