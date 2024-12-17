package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ClientRequestsService {
    List<ClientRequestDTO> getClientPendingRequests(int id);
    List<ClientRequestDTO> getClientInProgressRequests(int id);
    List<ClientRequestDTO> getClientCompletedRequests(int id);
}
