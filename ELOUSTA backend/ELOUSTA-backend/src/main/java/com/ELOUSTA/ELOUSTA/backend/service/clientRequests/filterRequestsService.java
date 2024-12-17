package com.ELOUSTA.ELOUSTA.backend.service.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.RequestsFiltering.LocationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class filterRequestsService {

    @Autowired
    private LocationFilter locationFilter;

    public List<RequestEntity> filterRequests(int id , String state,String query)
    {
        return this.locationFilter.Filter(id, state, query);
    }
}
