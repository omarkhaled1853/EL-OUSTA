package com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.IRequestSearch;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.TechnicianRequestSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;

@Service
public class TechnicianRequestSearchService implements RequestSearchService {
    @Autowired
    private final IRequestSearch requestSearch;

    public TechnicianRequestSearchService(TechnicianRequestSearch requestSearch) {
        this.requestSearch = requestSearch;
    }

    @Override
    public List<ViewRequestDTO> searchRequests(RequestPayload requestPayload) {
        List<RequestEntity> requestEntityList = requestSearch.search(
                requestPayload.getId(),
                requestPayload.getState(),
                requestPayload.getQuery()
        );

        return RequestEntityListToViewRequestDTOList(requestEntityList);
    }
}
