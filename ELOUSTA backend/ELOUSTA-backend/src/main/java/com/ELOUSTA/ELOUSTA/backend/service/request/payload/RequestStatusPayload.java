package com.ELOUSTA.ELOUSTA.backend.service.request.payload;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusPayload {

    private int id;
    private int clientId;
    private int techId;
}
