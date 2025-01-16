package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileDTO;

import java.util.Optional;

public interface TechnicianProfileService {
    Optional<TechnicianProfileDTO> getTechnician(Integer id);
    void removeTechnicianProfilePicture(Integer id);
    void resetTechnicianPassword(Integer id, String newPassword);
    void removeTechnicianPortfolio(Integer id, Integer portfolioId);
}
