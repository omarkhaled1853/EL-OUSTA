package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileDTO;

import java.util.Optional;

public interface TechnicianProfileService {
    Optional<TechnicianProfileDTO> getTechnician(Integer id);
}
