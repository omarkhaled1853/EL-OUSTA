package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads;


import lombok.Data;

@Data
public class SortRequestsPayload {

    private int id;
    private String type;
    private String state;
}
