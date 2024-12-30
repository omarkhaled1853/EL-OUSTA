package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSearch.ClientRequestsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientSearchRequestsService {
    @Autowired
    private ClientRequestsSearch clientRequestsSearch;

    public List<ClientRequestDTO> searchRequests(int id , String state, String query) {
        List<RequestEntity> requestEntityList = clientRequestsSearch.searchRequests(id, state, query);
        return RequestEntityListToClientRequestDTOList(requestEntityList);
    }
}
