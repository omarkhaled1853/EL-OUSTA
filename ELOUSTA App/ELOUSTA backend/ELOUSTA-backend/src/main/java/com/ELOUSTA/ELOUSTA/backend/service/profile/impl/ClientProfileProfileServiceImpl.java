package com.ELOUSTA.ELOUSTA.backend.service.profile.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ClientEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.service.profile.ClientProfileService;
import com.ELOUSTA.ELOUSTA.backend.utils.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.ELOUSTA.ELOUSTA.backend.utils.ImageHandler.generateUniquePhotoName;


@Service
public class ClientProfileProfileServiceImpl implements ClientProfileService {
    @Autowired
    private ClientRepository clientRepository;

    private static final String profilePath = "C:\\images\\profile\\";

    public Optional<ClientProfileDTO> getClient(Integer id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        int pendingRequests = clientRepository.getNumberOfPendingRequests(id);
        int inProgressRequests = clientRepository.getNumberOfInProgressRequests(id);
        int completedRequests = clientRepository.getNumberOfCompletedRequests(id);

        System.out.println(pendingRequests);
        System.out.println(inProgressRequests);
        System.out.println(completedRequests);

        return clientEntity.map(entity -> {
            try {
                return ClientMapper.clientEntityToClientDTO(
                        entity, pendingRequests, inProgressRequests, completedRequests);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void removeClientProfilePicture(Integer id) {
        clientRepository.deleteProfilePictureById(id);
    }

    @Override
    public void resetClientPassword(Integer id, String newPassword) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);

        if (clientEntityOptional.isEmpty()) {
            throw  new UsernameNotFoundException("Client with " + id + " not exist");
        }

        ClientEntity clientEntity = clientEntityOptional.get();

        clientEntity.setPassword(newPassword);

        clientRepository.save(clientEntity);
    }

    @Override
    public void uploadClientProfilePicture(Integer id, MultipartFile profilePicture) throws IOException {
        String profilePictureName = generateUniquePhotoName(profilePicture.getName());
        uploadClientProfilePicture(profilePictureName, profilePicture);

        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(id);

        if (clientEntityOptional.isEmpty()) {
            throw  new UsernameNotFoundException("Client with " + id + " not exist");
        }

        ClientEntity clientEntity = clientEntityOptional.get();

        clientEntity.setProfilePicture(profilePictureName);

        clientRepository.save(clientEntity);
    }

    private void uploadClientProfilePicture(String profilePictureName,
                                            MultipartFile profilePicture) throws IOException {
        String profilePicturePath = profilePath + profilePictureName;
        profilePicture.transferTo(new File(profilePicturePath));
    }
}
