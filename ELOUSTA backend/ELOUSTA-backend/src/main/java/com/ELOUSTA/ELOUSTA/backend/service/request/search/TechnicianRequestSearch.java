package com.ELOUSTA.ELOUSTA.backend.service.request.search;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianRequestSearch implements IRequestSearch{
    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<RequestEntity> search(int id, String state, String query) {

        return requestRepository.searchTechnicianRequestsByDescription(id, state, query);
    }
}
