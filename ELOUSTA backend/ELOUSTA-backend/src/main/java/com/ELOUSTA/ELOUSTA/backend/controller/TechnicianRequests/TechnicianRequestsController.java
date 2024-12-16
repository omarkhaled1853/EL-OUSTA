package com.ELOUSTA.ELOUSTA.backend.controller.TechnicianRequests;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.*;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.filterRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.generalTechnicianRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.searchRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.sortRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tech/requests")
public class TechnicianRequestsController {

    private final filterRequestsService filterService;
    private final searchRequestsService searchService;
    private final sortRequestsService sortService;
    private final generalTechnicianRequestsService generalService;


    @Autowired
    public TechnicianRequestsController(filterRequestsService filterService, searchRequestsService searchService, sortRequestsService sortService, generalTechnicianRequestsService generalService) throws ParseException {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
        this.generalService = generalService;
    }



    @GetMapping("/get/pending/{id}")
    private List<RequestEntity>getPendingRequests(@PathVariable int id)
    {
        return generalService.getAllRequestsByState(id,"PENDING");
    }


    @GetMapping("/get/inProgress/{id}")
    private List<RequestEntity>getInProgressRequests(@PathVariable int id)
    {
        return generalService.getAllRequestsByState(id,"IN-PROGRESS");
    }

    @GetMapping("/get/completed/{id}")

    private List<RequestEntity>getCompletedRequests(@PathVariable int id)
    {
        return generalService.getAllRequestsByState(id,"COMPLETED");
    }





    @PostMapping("/filter")

    private List<RequestEntity>filterRequests(@RequestBody RequestsPayload payload)
    {
        return this.filterService.filterRequests(payload.getId(),payload.getState(), payload.getQuery());
    }



    @PostMapping("/search")

    private List<RequestEntity>searchRequests(@RequestBody RequestsPayload payload)
    {
        return this.searchService.searchRequests(payload.getId(), payload.getState(), payload.getQuery());
    }


    @PostMapping("/sort")
    private List<RequestEntity>sortRequests(@RequestBody SortRequestsPayload payload)
    {
        return this.sortService.sortRequests(payload.getId(), payload.getType(), payload.getState());
    }

    @PostMapping("/Accept")
    private void receiveAccept(@RequestBody RequestStatusPayload acceptancePayload )
    {
        this.generalService.resolveAcceptance(acceptancePayload);
    }

    @PostMapping("refuse")
    private void receiveRefusal(@RequestBody RequestStatusPayload refusalPayload)
    {
        this.generalService.resolveRefusal(refusalPayload);
    }


}
