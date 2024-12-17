package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;

import java.util.Optional;

public interface ClientRequestsService {
    Optional<ClientRequestDTO> getClientPendingRequests(int id);
    Optional<ClientRequestDTO> getClientInProgressRequests(int id);
    Optional<ClientRequestDTO> getClientCompletedRequests(int id);
}
