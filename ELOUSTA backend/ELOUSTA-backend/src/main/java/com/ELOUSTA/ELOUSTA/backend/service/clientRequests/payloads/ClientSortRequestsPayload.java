package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.payloads;


import lombok.Data;

@Data
public class ClientSortRequestsPayload {

    private int id;
    private String type;
    private String state;
}
