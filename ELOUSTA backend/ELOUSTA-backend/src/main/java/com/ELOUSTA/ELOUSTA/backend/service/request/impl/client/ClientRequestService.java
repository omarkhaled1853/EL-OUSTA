package com.ELOUSTA.ELOUSTA.backend.service.request.impl.client;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestStatusPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityListToViewRequestDTOList;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.RequestEntityToOrderRequestDTO;

@Service
public class ClientRequestService implements RequestService {
    @Autowired
    private final RequestRepository requestRepository;
    private final ClientRepository clientRepository;
    private final NotificationService notificationService;

    public ClientRequestService(RequestRepository requestRepository,
                                ClientRepository clientRepository,
                                NotificationService notificationService) {

        this.requestRepository = requestRepository;
        this.clientRepository = clientRepository;
        this.notificationService = notificationService;
    }

    public void addRequest (OrderRequestDTO orderRequestDTO) {

        RequestEntity requestEntity = RequestEntityToOrderRequestDTO(orderRequestDTO);
        requestRepository.save(requestEntity);

        String message = orderRequestDTO.getDescription()
                +"\n" + "Location: " + orderRequestDTO.getLocation()
                +"\nstart date: " + orderRequestDTO.getStartDate()
                +"\nend date: " + orderRequestDTO.getEndDate();

        notificationService.sendNotificationToTechnician(message, orderRequestDTO.getTechId());
    }


    @Override
    public List<ViewRequestDTO> getPendingRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "PENDING");

        return RequestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getInProgressRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "IN-PROGRESS");

        return RequestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getCompletedRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "COMPLETED");

        return RequestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Transactional
    public void doneRequest(RequestStatusPayload donePayload) {

        RequestEntity request = requestRepository.findById(donePayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        request.setState("COMPLETED");

        requestRepository.save(request);

        ClientEntity client = clientRepository.findById(donePayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        String message = client.getUsername() + " Done the request ";

        notificationService.sendNotificationToClient(message, donePayload.getTechId());
    }

    @Transactional
    public void cancelRequest(RequestStatusPayload refusalPayload) {

        RequestEntity request = requestRepository.findById(refusalPayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        request.setState("CANCELLED");

        requestRepository.save(request);

        ClientEntity client = clientRepository.findById(refusalPayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        String message = client.getUsername() + " Cancel the request ";

        notificationService.sendNotificationToClient(message, refusalPayload.getTechId());
    }
}
