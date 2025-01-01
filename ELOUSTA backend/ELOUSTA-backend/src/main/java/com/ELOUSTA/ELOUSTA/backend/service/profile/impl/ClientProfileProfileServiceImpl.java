package com.ELOUSTA.ELOUSTA.backend.service.profile.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.service.profile.ClientProfileService;
import com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class ClientProfileProfileServiceImpl implements ClientProfileService {
    @Autowired
    private ClientRepository clientRepository;


    public Optional<ClientProfileDTO> getClient(Integer id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(entity -> {
            try {
                return ClientMapper.clientEntityToClientDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
