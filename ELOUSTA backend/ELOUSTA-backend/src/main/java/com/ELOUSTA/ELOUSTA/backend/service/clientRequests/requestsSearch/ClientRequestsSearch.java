package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSearch;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRequestsSearch {
    @Autowired
    private RequestRepository repository;

    public List<RequestEntity> searchRequests(int id, String state, String Query)
    {
        return repository.searchClientRequestsByDescription(id, state, Query);
    }
}
