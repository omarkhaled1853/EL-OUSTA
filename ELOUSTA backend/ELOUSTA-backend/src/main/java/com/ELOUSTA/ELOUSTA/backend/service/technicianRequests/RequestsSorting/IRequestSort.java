package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public interface IRequestSort {


    List<RequestEntity> sort(int id,String state);
}
