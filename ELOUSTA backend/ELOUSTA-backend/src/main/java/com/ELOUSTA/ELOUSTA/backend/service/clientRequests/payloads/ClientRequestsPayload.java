package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.payloads;


import lombok.Data;

@Data
public class ClientRequestsPayload {

    private int id;
    private String state;
    private String query;
}
