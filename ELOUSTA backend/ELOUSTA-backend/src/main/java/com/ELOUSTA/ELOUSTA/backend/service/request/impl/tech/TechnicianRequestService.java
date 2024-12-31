package com.ELOUSTA.ELOUSTA.backend.service.request.impl.tech;


import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
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

import static com.ELOUSTA.ELOUSTA.backend.utils.ComplaintMapper.complaintDTOToClientComplaintEntity;
import static com.ELOUSTA.ELOUSTA.backend.utils.ComplaintMapper.complaintDTOToTechnicinaComplaintEntity;
import static com.ELOUSTA.ELOUSTA.backend.utils.RequestMapper.requestEntityListToViewRequestDTOList;

@Service
public class TechnicianRequestService implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    private TechnicianRepository technicianRepository;
    private ClientRepository clientRepository;
    private ComplaintRepository complaintRepository;
    private NotificationService notificationService;

    @Override
    public List<ViewRequestDTO> getPendingRequests(int id) {

        List<RequestEntity> technicianRequestEntityList =
                requestRepository.getTechnicianRequestsByState(id, "PENDING");

        return requestEntityListToViewRequestDTOList(technicianRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getInProgressRequests(int id) {

        List<RequestEntity> technicianRequestEntityList =
                requestRepository.getTechnicianRequestsByState(id, "IN-PROGRESS");

        return requestEntityListToViewRequestDTOList(technicianRequestEntityList);
    }

    @Override
    public List<ViewRequestDTO> getCompletedRequests(int id) {

        List<RequestEntity> technicianRequestEntityList =
                requestRepository.getTechnicianRequestsByState(id, "COMPLETED");

        return requestEntityListToViewRequestDTOList(technicianRequestEntityList);
    }

    @Transactional
    public void resolveAcceptance(RequestStatusPayload acceptancePayload) {
        RequestEntity request = requestRepository.findById(acceptancePayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        request.setState("IN-PROGRESS");
        requestRepository.save(request);

        TechnicianEntity technician= technicianRepository.findById(acceptancePayload.getTechId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        //WILL be used in notification
        String message=technician.getUsername()+" Accepted your request ";
        ClientEntity client=clientRepository.findById(acceptancePayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        int clientID=client.getId();

        notificationService.sendNotificationToClient(message, clientID);

    }

    @Transactional
    public void resolveRefusal(RequestStatusPayload refusalPayload) {
        RequestEntity request = requestRepository.findById(refusalPayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        request.setState("CANCELLED");
        requestRepository.save(request);

        TechnicianEntity technician= technicianRepository.findById(refusalPayload.getTechId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        //WILL be used in notification
        String message=technician.getUsername()+" Refused your request ";
        ClientEntity client=clientRepository.findById(refusalPayload.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        int clientID=client.getId();

        notificationService.sendNotificationToClient(message, clientID);
    }

    @Transactional
    public void addComplaint(ComplaintDTO complaintDTO) {

        ComplaintEntity complaintEntity = complaintDTOToTechnicinaComplaintEntity(complaintDTO);

        complaintRepository.save(complaintEntity);

        TechnicianEntity technician = technicianRepository.findById(complaintDTO.getTechId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));

        String message = technician.getUsername() + " Complains you ";

        notificationService.sendNotificationToClient(message, complaintDTO.getClientId());
    }

}
