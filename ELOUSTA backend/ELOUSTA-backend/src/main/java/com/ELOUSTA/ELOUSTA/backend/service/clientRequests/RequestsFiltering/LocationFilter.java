package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.RequestsFiltering;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationFilter implements IRequestFilter {
    @Autowired
    private RequestRepository repository;

    @Override
    public List<RequestEntity> Filter(int id, String state, String query) {
        return this.repository.filterRequestsByLocation(id, state, query);
    }
}
