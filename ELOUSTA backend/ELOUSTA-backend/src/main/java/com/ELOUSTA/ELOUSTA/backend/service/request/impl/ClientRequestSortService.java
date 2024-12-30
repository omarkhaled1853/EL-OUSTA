package com.ELOUSTA.ELOUSTA.backend.service.request.impl;


import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByStartDate;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.IRequestSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;

@Service
public class ClientRequestSortService implements RequestSortService {

    @Autowired
    private final IRequestSort clientRequestSortByEndDate;
    private final IRequestSort clientRequestSortByStartDate;

    public ClientRequestSortService(ClientRequestSortByEndDate clientRequestSortByEndDate,
                                    ClientRequestSortByStartDate clientRequestSortByStartDate) {

        this.clientRequestSortByEndDate = clientRequestSortByEndDate;
        this.clientRequestSortByStartDate = clientRequestSortByStartDate;
    }

    public List<ViewRequestDTO>sortRequests(int id, RequestPayload requestPayload) {

        String type = requestPayload.getQuery().toLowerCase();

        if("startdate".equals(type)) {

            List<RequestEntity> requestEntityList = clientRequestSortByStartDate.sort(
                    id,
                    requestPayload.getState()
            );

            return RequestEntityListToClientRequestDTOList(requestEntityList);

        } else if ("enddate".equals(type)) {

            List<RequestEntity> requestEntityList = clientRequestSortByEndDate.sort(
                    id,
                    requestPayload.getState()
            );

            return RequestEntityListToClientRequestDTOList(requestEntityList);
        }

        return null;

    }


}
