package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;


import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSorting.ClientSortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSorting.ClientSortByStartDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientSortRequestsService {

    @Autowired
    private ClientSortByEndDate clientSortByEndDate;
    @Autowired
    private ClientSortByStartDate clientSortByStartDate;
    public List<ClientRequestDTO>sortRequests(int id, String type, String state)
    {
        type=type.toLowerCase();
        if(type.equals("startdate")) {
            List<RequestEntity> requestEntityList = clientSortByStartDate.sort(id, state);
            return RequestEntityListToClientRequestDTOList(requestEntityList);
        }
        else if (type.equals("enddate")) {
            List<RequestEntity> requestEntityList = clientSortByEndDate.sort(id, state);
            return RequestEntityListToClientRequestDTOList(requestEntityList);
        }
        else
            return null;

    }


}