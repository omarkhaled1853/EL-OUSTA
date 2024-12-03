package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.repository.ClientRepository;
import com.ELOUSTA.Profile_backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Optional<ClientDTO> getClient(Integer id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(this::clientEntityToClientDTO);
    }

    @Override
    public byte[] getProfilePhoto(String profilePhoto) throws IOException {
        String path = "C:\\images\\profile\\" + profilePhoto;
        return Files.readAllBytes(new File(path).toPath());
    }

    private ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        return ClientDTO.builder()
                .id(clientEntity.getId())
                .firstName(clientEntity.getFirstName())
                .lastName(clientEntity.getLastName())
                .profilePicture(clientEntity.getProfilePicture())
                .dob(clientEntity.getDob())
                .userName(clientEntity.getUserName())
                .phoneNumber(clientEntity.getPhoneNumber())
                .email(clientEntity.getEmail())
                .location(clientEntity.getGovernorate() + " "
                + clientEntity.getDistrict())
                .build();
    }
}
