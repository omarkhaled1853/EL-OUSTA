package com.ELOUSTA.ELOUSTA.backend.service.request.impl.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.ClientRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.IRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;

@Service
public class ClientRequestFilterService implements RequestFilterService {

    @Autowired
    private final ClientRequestFilter requestFilter;

    public ClientRequestFilterService(ClientRequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    public List<ViewRequestDTO> filterRequests(RequestPayload requestPayload) {

        List<RequestEntity> requestEntityList = requestFilter.Filter(
                requestPayload.getId(),
                requestPayload.getState(),
                requestPayload.getQuery()
        );

        return RequestEntityListToViewRequestDTOList(requestEntityList);
    }
}
