package com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsFiltering.TechnicianRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;

@Service
public class TechnicianRequestFilterService implements RequestFilterService {

    @Autowired
    private TechnicianRequestFilter technicianRequestFilter ;

    public List<ViewRequestDTO> filterRequests(RequestPayload payload) {

        List<RequestEntity> requestEntityList = technicianRequestFilter.Filter(
                payload.getId(),
                payload.getState(),
                payload.getQuery()
        );

        return RequestEntityListToViewRequestDTOList(requestEntityList);
    }
}
