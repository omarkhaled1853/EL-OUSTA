package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.repository.ClientRepository;
import com.ELOUSTA.Profile_backend.service.ClientService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private final String path = "C:\\images\\profile\\";

    public Optional<ClientDTO> getClient(Integer id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(entity -> {
            try {
                return clientEntityToClientDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private byte[] getProfilePhoto (String filename) throws IOException {
        String filePath = path + filename;
        return Files.readAllBytes(new File(filePath).toPath());
    }

    private ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) throws IOException {
        byte[] profilePhoto = getProfilePhoto(clientEntity.getProfilePicture());
        return ClientDTO.builder()
                .id(clientEntity.getId())
                .firstName(clientEntity.getFirstName())
                .lastName(clientEntity.getLastName())
                .profilePicture(profilePhoto)
                .dob(clientEntity.getDob())
                .userName(clientEntity.getUserName())
                .phoneNumber(clientEntity.getPhoneNumber())
                .email(clientEntity.getEmail())
                .location(clientEntity.getGovernorate() + " "
                + clientEntity.getDistrict())
                .build();
    }
}
