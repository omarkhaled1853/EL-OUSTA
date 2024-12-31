package com.ELOUSTA.ELOUSTA.backend.service.request.impl.client;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.ComplaintRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.request.RequestService;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestStatusPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ELOUSTA.ELOUSTA.backend.utils.ComplaintMapper.complaintDTOToComplaintEntity;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.requestEntityListToViewRequestDTOList;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.orderRequestDTOToRequestEntity;

@Service
public class ClientRequestService implements RequestService {
    @Autowired
    private final RequestRepository requestRepository;
    private final ClientRepository clientRepository;
    private final TechnicianRepository technicianRepository;
    private final NotificationService notificationService;
    private final ComplaintRepository complaintRepository;

    public ClientRequestService(RequestRepository requestRepository,
                                ClientRepository clientRepository,
                                TechnicianRepository technicianRepository,
                                NotificationService notificationService,
                                ComplaintRepository complaintRepository) {

        this.requestRepository = requestRepository;
        this.clientRepository = clientRepository;
        this.technicianRepository = technicianRepository;
        this.notificationService = notificationService;
        this.complaintRepository = complaintRepository;
    }

    public void addRequest (OrderRequestDTO orderRequestDTO) {

        RequestEntity requestEntity = orderRequestDTOToRequestEntity(orderRequestDTO);
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

        return requestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getInProgressRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "IN-PROGRESS");

        return requestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getCompletedRequests(int id) {

        List<RequestEntity> clientRequestEntityList =
                requestRepository.getClientRequestsByState(id, "COMPLETED");

        return requestEntityListToViewRequestDTOList(clientRequestEntityList);
    }

    @Transactional
    public void doneRequest(RequestStatusPayload donePayload) {

        RequestEntity request = requestRepository.findById(donePayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        if ("IN-PROGRESS".equals(request.getState())) {

            request.setState("COMPLETED");

        } else {

            throw new IllegalStateException("Request cannot be done in its current state: " + request.getState());
        }

        requestRepository.save(request);

        ClientEntity client = clientRepository.findById(donePayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        String message = client.getUsername() + " Done the request ";

        notificationService.sendNotificationToTechnician(message, donePayload.getTechId());
    }

    @Transactional
    public void cancelRequest(RequestStatusPayload refusalPayload) {

        RequestEntity request = requestRepository.findById(refusalPayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        if ("PENDING".equals(request.getState()) || "IN-PROGRESS".equals(request.getState())) {

            request.setState("CANCELLED");

        } else {

            throw new IllegalStateException("Request cannot be cancelled in its current state: " + request.getState());
        }

        requestRepository.save(request);

        ClientEntity client = clientRepository.findById(refusalPayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        String message = client.getUsername() + " Cancel the request ";

        notificationService.sendNotificationToTechnician(message, refusalPayload.getTechId());
    }

    @Transactional
    public void addComplaint(ComplaintDTO complaintDTO) {
        ClientEntity client = clientRepository.findById(complaintDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        TechnicianEntity technician = technicianRepository.findById(complaintDTO.getTechId())
                .orElseThrow(() -> new EntityNotFoundException("Technician not found"));

        ComplaintEntity complaintEntity =
                complaintDTOToComplaintEntity(complaintDTO, client, technician, true);


        complaintRepository.save(complaintEntity);


        String message = client.getUsername() + " Complains you ";

        notificationService.sendNotificationToTechnician(message, complaintDTO.getTechId());
    }
}
