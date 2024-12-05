package com.ELOUSTA.ELOUSTA.backend.service.profile;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;

import java.util.Optional;

public interface ClientProfileService {
    public Optional<ClientDTO> getClient(Integer id);
}
