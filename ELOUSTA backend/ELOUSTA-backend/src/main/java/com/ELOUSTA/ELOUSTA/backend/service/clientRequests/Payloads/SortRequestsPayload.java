package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.Payloads;


import lombok.Data;

@Data
public class SortRequestsPayload {

    private int id;
    private String type;
    private String state;
}
