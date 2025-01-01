package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;

import java.util.Optional;

public interface ClientProfileService {
    Optional<ClientProfileDTO> getClient(Integer id);
}
