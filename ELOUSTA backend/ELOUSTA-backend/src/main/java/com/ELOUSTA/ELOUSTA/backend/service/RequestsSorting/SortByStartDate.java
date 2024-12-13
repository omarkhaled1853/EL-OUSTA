package com.ELOUSTA.ELOUSTA.backend.service.RequestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public class SortByStartDate implements IRequestSort {


    @Override
    public List<RequestEntity> sort(List<RequestEntity> data) {
        data.sort((e1,e2)->e1.getStartDate().compareTo(e2.getStartDate()));
        return data;
    }
}
