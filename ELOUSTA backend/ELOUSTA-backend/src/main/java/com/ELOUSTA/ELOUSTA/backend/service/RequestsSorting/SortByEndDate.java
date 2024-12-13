package com.ELOUSTA.ELOUSTA.backend.service.RequestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public class SortByEndDate implements IRequestSort{
    @Override
    public List<RequestEntity> sort(List<RequestEntity> data) {
        data.sort((e1,e2)->e1.getEndDate().compareTo(e2.getEndDate()));
        return data;
    }
}
