package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.List;
import java.util.stream.Collectors;


public final class RequestMapper {
    public static RequestEntity orderRequestDTOToRequestEntity(
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

    public static List<ViewRequestDTO> requestEntityListToViewRequestDTOList(
            List<RequestEntity> requestEntityList) {
        return requestEntityList.stream().map( requestEntity ->
                        ViewRequestDTO.builder()
                                .id(requestEntity.getId())
                                .techId(requestEntity.getTechId())
                                .clientId(requestEntity.getUserId())
                                .state(requestEntity.getState())
                                .description(requestEntity.getDescription())
                                .location(requestEntity.getLocation())
                                .startDate(requestEntity.getStartDate())
                                .endDate(requestEntity.getEndDate())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
