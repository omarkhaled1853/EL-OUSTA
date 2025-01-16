package com.ELOUSTA.ELOUSTA.backend.service.profile;


import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ClientProfileService {
    Optional<ClientProfileDTO> getClient(Integer id);
    void removeClientProfilePicture(Integer id);
    void resetClientPassword(Integer id, String newPassword);
    void uploadClientProfilePicture(Integer id, MultipartFile profilePicture) throws IOException;
}
