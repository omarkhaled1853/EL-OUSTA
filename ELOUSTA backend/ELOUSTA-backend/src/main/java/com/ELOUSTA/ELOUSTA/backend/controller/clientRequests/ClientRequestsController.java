package com.ELOUSTA.ELOUSTA.backend.controller.clientRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.clientRequests.impl.ClientRequestsServiceImpl;
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

    @Autowired
    public ClientRequestsController(ClientRequestsServiceImpl clientRequestsService) {
        this.clientRequestsService = clientRequestsService;
    }

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
}
