package com.ELOUSTA.ELOUSTA.backend.service.request.sort;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianRequestSortByEndDate implements IRequestSort {
    @Autowired
    private RequestRepository repository;

    @Override
    public List<RequestEntity> sort(int id,String state) {
       return repository.sortTechnicianRequestsByEndDate(id,state);
    }
}
