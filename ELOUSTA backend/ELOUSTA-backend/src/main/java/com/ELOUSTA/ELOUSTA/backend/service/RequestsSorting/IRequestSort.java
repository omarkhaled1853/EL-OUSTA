package com.ELOUSTA.ELOUSTA.backend.service.RequestsSorting;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public interface IRequestSort {

    List<RequestEntity> sort(final List<RequestEntity> data);
}
