package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests;


import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.RequestStatusPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/tech/requests")
public class generalTechnicianRequestsService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private NotificationService notificationService;



    public List<RequestEntity>getAllRequestsByState(int id,String state)
    {
        return requestRepository.getRequestsByState(id,state);
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
}
