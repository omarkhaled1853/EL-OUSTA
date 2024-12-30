package com.ELOUSTA.ELOUSTA.backend.controller.request;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestFilterService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestSearchService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.impl.client.ClientRequestSortService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client/request")
public class ClientRequestController {
    @Autowired
    private final ClientRequestService clientRequestsService;
    private final ClientRequestFilterService requestFilterService;
    private final ClientRequestSearchService requestSearchService;
    private final ClientRequestSortService requestSortService;
    private final NotificationService notificationService;

    public ClientRequestController(ClientRequestService clientRequestsService,
                                   ClientRequestFilterService requestFilterService,
                                   ClientRequestSearchService requestSearchService,
                                   ClientRequestSortService requestSortService,
                                   NotificationService notificationService) {

        this.clientRequestsService = clientRequestsService;
        this.requestFilterService = requestFilterService;
        this.requestSearchService = requestSearchService;
        this.requestSortService = requestSortService;
        this.notificationService = notificationService;
    }


    @PostMapping("/addRequest")
    public ResponseEntity<?> addRequest(@RequestBody OrderRequestDTO orderRequestDto) {
        clientRequestsService.addRequest(orderRequestDto);

        String message = orderRequestDto.getDescription()
                +"\n" + "Location: " + orderRequestDto.getLocation()
                +"\nstart date: " + orderRequestDto.getStartDate()
                +"\nend date: " + orderRequestDto.getEndDate();

        notificationService.sendNotificationToTechnician(message, orderRequestDto.getTechId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Request successfully saved");
    }

    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Integer id) {

        List<ViewRequestDTO> pendingViewRequestDtoList =
                clientRequestsService.getPendingRequests(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingViewRequestDtoList);
    }

    @GetMapping("/inProgress/{id}")
    public ResponseEntity<?> getInProgressRequests(@PathVariable Integer id) {

        List<ViewRequestDTO> pendingViewRequestDtoList =
                clientRequestsService.getInProgressRequests(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingViewRequestDtoList);
    }

    @GetMapping("/completed/{id}")
    public ResponseEntity<?> getCompletedRequests(@PathVariable Integer id) {

        List<ViewRequestDTO> pendingViewRequestDtoList =
                clientRequestsService.getCompletedRequests(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pendingViewRequestDtoList);
    }

    @GetMapping("/filter")
    private ResponseEntity<?>filterRequests(@RequestParam RequestPayload payload) {

        List<ViewRequestDTO> clientFilterRequests = requestFilterService.filterRequests(payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clientFilterRequests);
    }



    @GetMapping("/search")

    private ResponseEntity<?>searchRequests(@RequestParam RequestPayload payload) {

        List<ViewRequestDTO> clientSearchRequests =
                requestSearchService.searchRequests(payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clientSearchRequests);
    }


    @GetMapping("/sort")
    private ResponseEntity<?>sortRequests(@RequestParam RequestPayload payload) {

        List<ViewRequestDTO> clientSortRequests =
                requestSortService.sortRequests(payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clientSortRequests);
    }
}
