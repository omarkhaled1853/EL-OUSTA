package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsFiltering.ClientLocationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientFilterRequestsService {

    @Autowired
    private ClientLocationFilter clientLocationFilter;

    public List<ClientRequestDTO> filterRequests(int id , String state, String query) {
        List<RequestEntity> requestEntityList = clientLocationFilter.Filter(id, state, query);
        return RequestEntityListToClientRequestDTOList(requestEntityList);
    }
}
