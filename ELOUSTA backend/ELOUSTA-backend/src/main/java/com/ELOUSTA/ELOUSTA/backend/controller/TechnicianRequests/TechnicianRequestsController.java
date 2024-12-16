package com.ELOUSTA.ELOUSTA.backend.controller.TechnicianRequests;

import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.filterRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.searchRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.sortRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    //TODO: filters


    //TODO: searching


    //TODO: sorting

}
