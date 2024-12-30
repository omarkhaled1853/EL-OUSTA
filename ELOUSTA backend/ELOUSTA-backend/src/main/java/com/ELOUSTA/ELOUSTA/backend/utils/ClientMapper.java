package com.ELOUSTA.ELOUSTA.backend.utils;

import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.OrderRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.ViewRequestDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.getProfilePhoto;

public final class ClientMapper {
    private static final String path = "C:\\images\\profile\\";

    public static ClientProfileDTO clientEntityToClientDTO(ClientEntity clientEntity) throws IOException {
        byte[] profilePhoto = getProfilePhoto(clientEntity.getProfilePicture(), path);
        return ClientProfileDTO.builder()
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
}
