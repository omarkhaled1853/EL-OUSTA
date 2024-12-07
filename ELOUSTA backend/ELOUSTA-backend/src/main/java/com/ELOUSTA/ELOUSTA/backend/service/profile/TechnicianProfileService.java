package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;

import java.util.Optional;

public interface TechnicianProfileService {
    Optional<TechnicianProfileProfileDTO> getTechnician(Integer id);
}
