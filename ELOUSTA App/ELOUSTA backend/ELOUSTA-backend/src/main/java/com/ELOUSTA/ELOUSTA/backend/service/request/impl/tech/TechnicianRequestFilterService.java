package com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.TechnicianRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.requestEntityListToViewRequestDTOList;

@Service
public class TechnicianRequestFilterService implements RequestFilterService {

    @Autowired
    private final TechnicianRequestFilter technicianRequestFilter ;

    public TechnicianRequestFilterService(TechnicianRequestFilter technicianRequestFilter) {
        this.technicianRequestFilter = technicianRequestFilter;
    }

    public List<ViewRequestDTO> filterRequests(RequestPayload payload) {

        List<RequestEntity> requestEntityList = technicianRequestFilter.Filter(
                payload.getId(),
                payload.getState(),
                payload.getQuery()
        );

        return requestEntityListToViewRequestDTOList(requestEntityList);
    }
}
