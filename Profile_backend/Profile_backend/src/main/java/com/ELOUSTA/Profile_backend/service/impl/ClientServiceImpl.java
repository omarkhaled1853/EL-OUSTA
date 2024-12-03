package com.ELOUSTA.Profile_backend.service.impl;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;
import com.ELOUSTA.Profile_backend.repository.ClientRepository;
import com.ELOUSTA.Profile_backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<ClientDTO> getClient(Integer id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(this::clientEntityToClientDTO);
    }

    private ClientDTO clientEntityToClientDTO(ClientEntity clientEntity) {
        return ClientDTO.builder()
                .name(clientEntity.getFirstName() + " "
                + clientEntity.getLastName())
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
