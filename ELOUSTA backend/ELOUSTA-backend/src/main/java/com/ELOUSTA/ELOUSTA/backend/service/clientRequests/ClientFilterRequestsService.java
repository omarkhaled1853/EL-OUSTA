package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsFiltering.LocationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientFilterRequestsService {

    @Autowired
    private LocationFilter locationFilter;

    public List<ClientRequestDTO> filterRequests(int id , String state, String query) {
        List<RequestEntity> requestEntityList = locationFilter.Filter(id, state, query);
        return RequestEntityListToClientRequestDTOList(requestEntityList);
    }
}
