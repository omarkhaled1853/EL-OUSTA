package com.ELOUSTA.ELOUSTA.backend.service.request.impl.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.ClientRequestSearch;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.IRequestSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;

@Service
public class ClientRequestSearchService implements RequestSearchService {
    @Autowired
    private final IRequestSearch requestSearch;

    public ClientRequestSearchService(ClientRequestSearch requestSearch) {
        this.requestSearch = requestSearch;
    }

    public List<ViewRequestDTO> searchRequests(RequestPayload requestPayload) {

        List<RequestEntity> requestEntityList = requestSearch.search(
                requestPayload.getId(),
                requestPayload.getState(),
                requestPayload.getQuery()
        );

        return RequestEntityListToViewRequestDTOList(requestEntityList);
    }
}
