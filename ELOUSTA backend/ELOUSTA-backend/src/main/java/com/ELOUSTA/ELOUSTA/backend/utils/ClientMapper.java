package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.getProfilePhoto;

public final class ClientMapper {
    private static final String path = "C:\\images\\profile\\";

    public static ClientProfileProfileDTO clientEntityToClientDTO(ClientEntity clientEntity) throws IOException {
        byte[] profilePhoto = getProfilePhoto(clientEntity.getProfilePicture(), path);
        return ClientProfileProfileDTO.builder()
                .id(clientEntity.getId())
                .firstName(clientEntity.getFirstName())
                .lastName(clientEntity.getLastName())
                .profilePicture(profilePhoto)
                .dob(clientEntity.getDob())
                .userName(clientEntity.getUsername())
                .phoneNumber(clientEntity.getPhoneNumber())
                .email(clientEntity.getEmailAddress())
                .city(clientEntity.getCity())
                .build();
    }
    
    public static List<ClientRequestDTO> RequestEntityListToClientRequestDTOList(
            List<RequestEntity> requestEntityList) {
        return requestEntityList.stream().map( requestEntity ->
                ClientRequestDTO.builder()
                        .id(requestEntity.getId())
                        .techId(requestEntity.getTechId())
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
