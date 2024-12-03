package com.ELOUSTA.Profile_backend.service;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.entity.ClientEntity;

import java.util.Optional;

public interface ClientService {
    public Optional<ClientDTO> getClient(Integer id);
}
