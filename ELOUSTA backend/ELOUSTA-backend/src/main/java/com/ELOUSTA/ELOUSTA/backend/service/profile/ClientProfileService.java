package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.ClientDTO;

import java.util.Optional;

public interface ClientProfileService {
    Optional<ClientDTO> getClient(Integer id);
}
