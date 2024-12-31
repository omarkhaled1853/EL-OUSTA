package com.ELOUSTA.ELOUSTA.backend.controller.request;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
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
    private final TechnicianRequestFilterService filterService;
    private final TechnicianRequestSearchService searchService;
    private final TechnicianRequestSortService sortService;
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
    public List<ViewRequestDTO> getPendingRequests(@PathVariable int id) {

        return generalService.getPendingRequests(id);
    }


    @GetMapping("/get/inProgress/{id}")
    public List<ViewRequestDTO> getInProgressRequests(@PathVariable int id) {
        return generalService.getInProgressRequests(id);
    }

    @GetMapping("/get/completed/{id}")

    public List<ViewRequestDTO> getCompletedRequests(@PathVariable int id) {

        return generalService.getCompletedRequests(id);
    }


    @PostMapping("/filter")
    public List<ViewRequestDTO> filterRequests(@RequestBody RequestPayload payload) {

        return filterService.filterRequests(payload);
    }


    @PostMapping("/search")
    public List<ViewRequestDTO> searchRequests(@RequestBody RequestPayload payload) {

        return this.searchService.searchRequests(payload);
    }


    @PostMapping("/sort")
    public List<ViewRequestDTO> sortRequests(@RequestBody RequestPayload payload) {

        return sortService.sortRequests(payload);
    }

    @PostMapping("/accept")
    public void receiveAccept (@RequestBody RequestStatusPayload acceptancePayload ) {

        this.generalService.resolveAcceptance(acceptancePayload);
    }

    @PostMapping("/refuse")
    public void receiveRefusal(@RequestBody RequestStatusPayload refusalPayload) {

        this.generalService.resolveRefusal(refusalPayload);
    }

    @PostMapping("/complain")
    public void addComplaint(@RequestBody ComplaintDTO complaintDTO) {

        generalService.addComplaint(complaintDTO);
    }
}
