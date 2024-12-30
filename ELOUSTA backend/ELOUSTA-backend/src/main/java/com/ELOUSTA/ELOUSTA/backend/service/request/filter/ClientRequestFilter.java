package com.ELOUSTA.ELOUSTA.backend.service.request.filter;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRequestFilter implements IRequestFilter {
    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<RequestEntity> Filter(int id, String state, String query) {

        return requestRepository.filterClientRequestsByLocation(id, state, query);
    }
}
