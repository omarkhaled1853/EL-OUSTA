package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.payloads;


import lombok.Data;

@Data
public class RequestStatusPayload {

    private int id;
    private int clientId;
    private int techId;
}
