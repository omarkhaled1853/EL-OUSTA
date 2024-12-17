package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientRequestsService;

import java.util.Optional;

public class ClientRequestsServiceImpl implements ClientRequestsService {
    @Override
    public Optional<ClientRequestDTO> getClientPendingRequests(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<ClientRequestDTO> getClientInProgressRequests(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<ClientRequestDTO> getClientCompletedRequests(int id) {
        return Optional.empty();
    }
}
