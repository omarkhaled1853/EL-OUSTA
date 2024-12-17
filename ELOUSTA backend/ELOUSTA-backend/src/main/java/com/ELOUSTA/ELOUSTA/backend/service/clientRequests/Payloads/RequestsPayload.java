package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.Payloads;


import lombok.Data;

@Data
public class RequestsPayload {

    private int id;
    private String state;
    private String query;
}
