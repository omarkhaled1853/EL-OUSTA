package com.ELOUSTA.ELOUSTA.backend.service.profile;

import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;

import java.util.Optional;

public interface TechnicianProfileService {
    Optional<TechnicianDTO> getTechnician(Integer id);
}
