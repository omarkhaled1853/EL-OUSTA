package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads;


import lombok.Data;

@Data
public class RequestStatusPayload {

    private int id;
    private int clientId;
    private int techId;
}
