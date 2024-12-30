package com.ELOUSTA.ELOUSTA.backend.service.request;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;

import java.util.List;

public interface RequestSortService {
    List<ViewRequestDTO> sortRequests(int id, RequestPayload requestPayload);
}
