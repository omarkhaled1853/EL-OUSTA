package com.ELOUSTA.ELOUSTA.backend.controller.TechnicianRequests;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.*;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.filterRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.searchRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.sortRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tech/requests")
public class TechnicianRequestsController {

    private final filterRequestsService filterService;

    private final searchRequestsService searchService;
    private final sortRequestsService sortService;

    @Autowired
    public TechnicianRequestsController(filterRequestsService filterService, searchRequestsService searchService, sortRequestsService sortService) {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
    }

    //TODO : Getters

    @GetMapping("/get/pending")
    private List<RequestEntity>getPendingRequests()
    {
        return null;
    }


    @GetMapping("/get/inProgress")
    private List<RequestEntity>getInProgressRequests()
    {
        return null;
    }

    @GetMapping("/get/completed")

    private List<RequestEntity>getCompletedRequests()
    {
        return null;
    }





    @GetMapping("/filter")

    private List<RequestEntity>filterRequests(@RequestBody RequestsPayload payload)
    {
        return this.filterService.filterRequests(payload.getId(),payload.getState(), payload.getQuery());
    }



    @GetMapping("/search")

    private List<RequestEntity>searchRequests(@RequestBody RequestsPayload payload)
    {
        return this.searchService.searchRequests(payload.getId(), payload.getState(), payload.getQuery());
    }


    @GetMapping("/sort")
    private List<RequestEntity>sortRequests(@RequestBody SortRequestsPayload payload)
    {
        return this.sortService.sortRequests(payload.getId(), payload.getType(), payload.getState());
    }

}
