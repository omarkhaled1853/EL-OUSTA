package com.ELOUSTA.ELOUSTA.backend.service.requestservice;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private RequestRepo requestRepo;
    public RequestEntity Saverequest(RequestEntity request) {
        return requestRepo.save(request);
    }
}
