package com.ELOUSTA.ELOUSTA.backend.controller.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
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

    @Autowired
    public ClientRequestsController(ClientRequestsServiceImpl clientRequestsService,
                                    ClientSearchRequestsService clientSearchRequestsService,
                                    ClientSortRequestsService clientSortRequestsService,
                                    ClientFilterRequestsService clientFilterRequestsService) {
        this.clientRequestsService = clientRequestsService;
        this.clientSearchRequestsService = clientSearchRequestsService;
        this.clientSortRequestsService = clientSortRequestsService;
        this.clientFilterRequestsService = clientFilterRequestsService;
    }


    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Integer id) {
        List<ClientRequestDTO> pendingClientRequestDtoList = clientRequestsService.getClientPendingRequests(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingClientRequestDtoList);
    }

    @GetMapping("/inProgress/{id}")
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
    private ResponseEntity<?>filterRequests(@RequestBody RequestsPayload payload) {
        List<ClientRequestDTO> clientFilterRequests =
                clientFilterRequestsService.filterRequests(payload.getId(),payload.getState(), payload.getQuery());
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientFilterRequests);
    }



    @PostMapping("/search")

    private ResponseEntity<?>searchRequests(@RequestBody RequestsPayload payload) {
        List<ClientRequestDTO> clientSearchRequests =
                clientSearchRequestsService.searchRequests(payload.getId(), payload.getState(), payload.getQuery());
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientSearchRequests);
    }


    @PostMapping("/sort")
    private ResponseEntity<?>sortRequests(@RequestBody SortRequestsPayload payload) {
        List<ClientRequestDTO> clientSortRequests =
                clientSortRequestsService.sortRequests(payload.getId(), payload.getType(), payload.getState());
        return ResponseEntity.status(HttpStatus.OK)
                .body(clientSortRequests);
    }
}
