package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSearch.RequestsSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientSearchRequestsService {
    @Autowired
    private RequestsSearch requestsSearch;

    public List<RequestEntity> searchRequests(int id , String state, String query)
    {
        return this.requestsSearch.searchRequests(id, state, query);
    }
}
