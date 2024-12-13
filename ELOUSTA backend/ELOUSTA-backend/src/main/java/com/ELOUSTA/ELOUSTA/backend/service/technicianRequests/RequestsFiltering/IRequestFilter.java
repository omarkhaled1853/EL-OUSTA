package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsFiltering;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.sql.Date;
import java.util.List;

public interface IRequestFilter {

    List<RequestEntity>Filter(int id,String state,String query);
}
