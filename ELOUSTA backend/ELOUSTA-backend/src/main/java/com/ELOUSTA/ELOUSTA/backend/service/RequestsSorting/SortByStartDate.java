package com.ELOUSTA.ELOUSTA.backend.service.RequestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SortByStartDate implements IRequestSort {

    @Autowired
    private RequestRepository repository;

    @Override
    public List<RequestEntity> sort(int id) {
       return repository.sortRequestsByStartDate(id);
    }
}
