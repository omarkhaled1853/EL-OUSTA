package com.ELOUSTA.ELOUSTA.backend.service.request.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.ClientRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.filter.IRequestFilter;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.ClientRequestSearch;
import com.ELOUSTA.ELOUSTA.backend.service.request.search.IRequestSearch;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.ClientRequestSortByStartDate;
import com.ELOUSTA.ELOUSTA.backend.service.request.sort.IRequestSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper.RequestEntityListToClientRequestDTOList;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityToOrderRequestDTO;

@Service
public class ClientRequestService implements RequestService {
    @Autowired
    private final RequestRepository requestRepository;

    public ClientRequestService(RequestRepository requestRepository) {

        this.requestRepository = requestRepository;
    }

    public void addRequest (OrderRequestDTO orderRequestDTO) {
        RequestEntity requestEntity = RequestEntityToOrderRequestDTO(orderRequestDTO);
        requestRepository.save(requestEntity);
    }


    @Override
    public List<ViewRequestDTO> getPendingRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "PENDING");

        return RequestEntityListToClientRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getInProgressRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "IN-PROGRESS");

        return RequestEntityListToClientRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getCompletedRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "COMPLETED");

        return RequestEntityListToClientRequestDTOList(clientRequestEntityList);
    }
}
