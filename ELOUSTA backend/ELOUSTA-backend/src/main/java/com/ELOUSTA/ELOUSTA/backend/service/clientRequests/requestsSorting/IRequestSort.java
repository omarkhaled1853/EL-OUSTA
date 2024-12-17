package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.requestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public interface IRequestSort {


    List<RequestEntity> sort(int id,String state);
}
