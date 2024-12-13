package com.ELOUSTA.ELOUSTA.backend.service.RequestsFiltering;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.sql.Date;
import java.util.List;

public interface IRequestFilter {

    List<RequestEntity>Filter(final Date date,final List<RequestEntity> data);
}
