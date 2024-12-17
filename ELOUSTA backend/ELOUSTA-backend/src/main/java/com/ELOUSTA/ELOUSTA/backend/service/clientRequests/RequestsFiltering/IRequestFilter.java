package com.ELOUSTA.ELOUSTA.backend.service.clientRequests.RequestsFiltering;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public interface IRequestFilter {

    List<RequestEntity>Filter(int id,String state,String query);
}
