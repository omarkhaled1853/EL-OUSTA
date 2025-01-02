package com.ELOUSTA.ELOUSTA.backend.service.request.search;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;

public interface IRequestSearch {
    List<RequestEntity> search (int id, String state, String query);
}
