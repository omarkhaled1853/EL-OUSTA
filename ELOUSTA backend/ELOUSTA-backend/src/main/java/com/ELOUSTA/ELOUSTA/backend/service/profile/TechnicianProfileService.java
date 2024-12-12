package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;

import java.util.Optional;

public interface TechnicianProfileService {
    Optional<TechnicianProfileProfileDTO> getTechnician(Integer id);
    void removeTechnicianProfilePicture(Integer id);
    void resetTechnicianPassword(Integer id, String newPassword);
    void removeTechnicianPortfolio(Integer id, Integer portfolioId);
}
