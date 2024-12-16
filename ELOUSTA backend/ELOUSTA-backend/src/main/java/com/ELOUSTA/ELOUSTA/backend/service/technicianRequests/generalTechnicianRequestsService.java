package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests;


import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.Payloads.RequestStatusPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/tech/requests")
public class generalTechnicianRequestsService {

    @Autowired
    private RequestRepository repository;


    public List<RequestEntity>getAllRequestsByState(int id,String state)
    {
        return repository.getRequestsByState(id,state);
    }

    @Transactional
    public void resolveAcceptance(RequestStatusPayload acceptanceObject) {
        RequestEntity request = repository.findById(acceptanceObject.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        request.setState("IN-PROGRESS");
        repository.save(request);
    }

    @Transactional
    public void resolveRefusal(RequestStatusPayload refusalPayload) {
        RequestEntity request = repository.findById(refusalPayload.getId())
                .orElseThrow(() -> new EntityNotFoundException("NO such Data"));
        request.setState("CANCELLED");
        repository.save(request);
    }
}
