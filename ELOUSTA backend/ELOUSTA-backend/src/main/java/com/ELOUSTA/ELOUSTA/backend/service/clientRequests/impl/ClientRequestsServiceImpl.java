package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientRequestsServiceImpl implements ClientRequestsService {
    @Autowired
    private RequestRepository requestRepository;


    @Override
    public List<ClientRequestDTO> getClientPendingRequests(int id) {
        List<RequestEntity> clientRequestEntityList = requestRepository.getClientRequestsByState(id, "PENDING");
        return RequestEntityListToClientRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ClientRequestDTO> getClientInProgressRequests(int id) {
        return null;
    }

    @Override
    public List<ClientRequestDTO> getClientCompletedRequests(int id) {
        return null;
    }
}
