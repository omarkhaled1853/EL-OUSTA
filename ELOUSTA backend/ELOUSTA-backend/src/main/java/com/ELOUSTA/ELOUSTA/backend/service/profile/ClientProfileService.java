package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileProfileDTO;

import java.util.Optional;

public interface ClientProfileService {
    Optional<ClientProfileProfileDTO> getClient(Integer id);
    void removeClientProfilePhoto(Integer id);
}
