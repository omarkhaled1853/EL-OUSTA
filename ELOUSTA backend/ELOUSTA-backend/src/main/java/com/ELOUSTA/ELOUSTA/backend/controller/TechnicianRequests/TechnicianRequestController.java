package com.ELOUSTA.ELOUSTA.backend.controller.TechnicianRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech.TechnicianRequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestStatusPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tech/requests")
public class TechnicianRequestController {

    @Autowired
    private final RequestFilterService filterService;
    private final RequestSearchService searchService;
    private final RequestSortService sortService;
    private final TechnicianRequestService generalService;


    public TechnicianRequestController(TechnicianRequestFilterService filterService,
                                       TechnicianRequestSearchService searchService,
                                       TechnicianRequestSortService sortService,
                                       TechnicianRequestService generalService) {

        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
        this.generalService = generalService;
    }



    @GetMapping("/get/pending/{id}")
    private List<ViewRequestDTO> getPendingRequests(@PathVariable int id) {

        return generalService.getPendingRequests(id);
    }


    @GetMapping("/get/inProgress/{id}")
    private List<ViewRequestDTO> getInProgressRequests(@PathVariable int id) {
        return generalService.getInProgressRequests(id);
    }

    @GetMapping("/get/completed/{id}")

    private List<ViewRequestDTO> getCompletedRequests(@PathVariable int id) {

        return generalService.getCompletedRequests(id);
    }


    @PostMapping("/filter")
    private List<ViewRequestDTO> filterRequests(@RequestBody RequestPayload payload) {

        return filterService.filterRequests(payload);
    }


    @PostMapping("/search")
    private List<ViewRequestDTO> searchRequests(@RequestBody RequestPayload payload) {

        return this.searchService.searchRequests(payload);
    }


    @PostMapping("/sort")
    private List<ViewRequestDTO> sortRequests(@RequestBody RequestPayload payload) {

        return sortService.sortRequests(payload);
    }

    @PostMapping("/accept")
    private void receiveAccept (@RequestBody RequestStatusPayload acceptancePayload ) {

        this.generalService.resolveAcceptance(acceptancePayload);
    }

    @PostMapping("/refuse")
    private void receiveRefusal(@RequestBody RequestStatusPayload refusalPayload) {

        this.generalService.resolveRefusal(refusalPayload);
    }


}
