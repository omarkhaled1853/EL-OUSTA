package com.ELOUSTA.ELOUSTA.backend.controller.profile;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.ClientProfileProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.service.profile.ClientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/client/profile")
public class ClientProfileController {
    private final ClientProfileService clientService;

    @Autowired
    public ClientProfileController(ClientProfileService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProfileProfileDTO> getClient(@PathVariable Integer id) {
        Optional<ClientProfileProfileDTO> clientDTO = clientService.getClient(id);
        return clientDTO.map(client -> new ResponseEntity<ClientProfileProfileDTO>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<ClientProfileProfileDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeClientProfilePicture(@PathVariable Integer id) {
        clientService.removeClientProfilePhoto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
