package com.ELOUSTA.Profile_backend.service;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;

import java.util.Optional;

public interface ClientService {
    public Optional<ClientDTO> getClient(Integer id);
}
