package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads;


import lombok.Data;

@Data
public class filterRequestsPayload {

    private int id;
    private String state;
    private String query;
}
