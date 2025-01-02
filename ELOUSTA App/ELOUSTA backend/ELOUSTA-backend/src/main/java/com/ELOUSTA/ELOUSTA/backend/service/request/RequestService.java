package com.ELOUSTA.ELOUSTA.backend.service.request;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.service.request.payload.RequestPayload;

import java.util.List;

public interface RequestService {
    List<ViewRequestDTO> getPendingRequests(int id);
    List<ViewRequestDTO> getInProgressRequests(int id);
    List<ViewRequestDTO> getCompletedRequests(int id);
}
