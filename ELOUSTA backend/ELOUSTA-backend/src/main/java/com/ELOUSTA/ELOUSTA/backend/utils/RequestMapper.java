package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;


public final class RequestMapper {
    public static RequestEntity RequestEntityToOrderRequestDTO(
            OrderRequestDTO orderRequestDTO) {
        return RequestEntity.builder()
                .userId(orderRequestDTO.getClientId())
                .techId(orderRequestDTO.getTechId())
                .startDate(orderRequestDTO.getStartDate())
                .endDate(orderRequestDTO.getEndDate())
                .Location(orderRequestDTO.getLocation())
                .description(orderRequestDTO.getDescription())
                .state("PENDING")
                .build();
    }
}
