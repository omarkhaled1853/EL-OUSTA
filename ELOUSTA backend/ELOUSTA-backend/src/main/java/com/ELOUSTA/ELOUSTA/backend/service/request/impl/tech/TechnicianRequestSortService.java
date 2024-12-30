package com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech;


import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;

@Service
public class TechnicianRequestSortService implements RequestSortService {

    @Autowired
    private final TechnicianRequestSortByEndDate techRequestSortByEndDate;
    private final TechnicianRequestSortByStartDate techRequestSortByStartDate;

    public TechnicianRequestSortService (TechnicianRequestSortByEndDate techRequestSortByEndDate,
                                    TechnicianRequestSortByStartDate techRequestSortByStartDate) {

        this.techRequestSortByEndDate = techRequestSortByEndDate;
        this.techRequestSortByStartDate = techRequestSortByStartDate;
    }

    public List<ViewRequestDTO>sortRequests(RequestPayload requestPayload) {

        String type = requestPayload.getQuery().toLowerCase();

        if("startdate".equals(type)) {

            List<RequestEntity> requestEntityList = techRequestSortByStartDate.sort(
                    requestPayload.getId(),
                    requestPayload.getState()
            );

            return RequestEntityListToViewRequestDTOList(requestEntityList);

        } else if ("enddate".equals(type)) {

            List<RequestEntity> requestEntityList = techRequestSortByEndDate.sort(
                    requestPayload.getId(),
                    requestPayload.getState()
            );

            return RequestEntityListToViewRequestDTOList(requestEntityList);
        }

        return null;

    }
}
