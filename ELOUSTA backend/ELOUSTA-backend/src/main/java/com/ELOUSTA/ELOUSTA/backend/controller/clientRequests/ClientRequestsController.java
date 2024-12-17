package com.ELOUSTA.ELOUSTA.backend.controller.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientFilterRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientSearchRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.ClientSortRequestsService;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.impl.ClientRequestsServiceImpl;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.RequestsPayload;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.SortRequestsPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client/requests")
public class ClientRequestsController {
    private final ClientRequestsServiceImpl clientRequestsService;
    private final ClientSearchRequestsService clientSearchRequestsService;
    private final ClientSortRequestsService clientSortRequestsService;
    private final ClientFilterRequestsService clientFilterRequestsService;

    public ClientRequestsController(ClientRequestsServiceImpl clientRequestsService,
                                    ClientSearchRequestsService clientSearchRequestsService,
                                    ClientSortRequestsService clientSortRequestsService,
                                    ClientFilterRequestsService clientFilterRequestsService) {
        this.clientRequestsService = clientRequestsService;
        this.clientSearchRequestsService = clientSearchRequestsService;
        this.clientSortRequestsService = clientSortRequestsService;
        this.clientFilterRequestsService = clientFilterRequestsService;
    }

    @Autowired

    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Integer id) {
        List<ClientRequestDTO> pendingClientRequestDtoList = clientRequestsService.getClientPendingRequests(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingClientRequestDtoList);
    }

    @GetMapping("/inprogress/{id}")
    public ResponseEntity<?> getInProgressRequests(@PathVariable Integer id) {
        List<ClientRequestDTO> pendingClientRequestDtoList = clientRequestsService.getClientInProgressRequests(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingClientRequestDtoList);
    }

    @GetMapping("/completed/{id}")
    public ResponseEntity<?> getCompletedRequests(@PathVariable Integer id) {
        List<ClientRequestDTO> pendingClientRequestDtoList = clientRequestsService.getClientCompletedRequests(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingClientRequestDtoList);
    }

    @PostMapping("/filter")

    private List<RequestEntity>filterRequests(@RequestBody RequestsPayload payload)
    {
        return this.clientFilterRequestsService.filterRequests(payload.getId(),payload.getState(), payload.getQuery());
    }



    @PostMapping("/search")

    private List<RequestEntity>searchRequests(@RequestBody RequestsPayload payload)
    {
        return this.clientSearchRequestsService.searchRequests(payload.getId(), payload.getState(), payload.getQuery());
    }


    @PostMapping("/sort")
    private List<RequestEntity>sortRequests(@RequestBody SortRequestsPayload payload)
    {
        return this.clientSortRequestsService.sortRequests(payload.getId(), payload.getType(), payload.getState());
    }
}
