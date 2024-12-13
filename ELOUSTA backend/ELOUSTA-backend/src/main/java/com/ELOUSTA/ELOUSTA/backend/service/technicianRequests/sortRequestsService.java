package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests;


import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsSorting.SortByEndDate;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsSorting.SortByStartDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class sortRequestsService {

    @Autowired
    private SortByEndDate sortByEndDate;
    @Autowired
    private SortByStartDate sortByStartDate;
    public List<RequestEntity>sortRequests(int id,String type,String state)
    {
        type=type.toLowerCase();
        if(type.equals("startdate"))
            return sortByStartDate.sort(id, state);
        else if (type.equals("enddate"))
            return sortByEndDate.sort(id,state);
        else
            return null;

    }


}
