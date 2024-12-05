package com.ELOUSTA.ELOUSTA.backend.controller.profile;

import com.ELOUSTA.ELOUSTA.backend.dto.ClientDTO;
import com.ELOUSTA.ELOUSTA.backend.service.profile.ClientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/client")
public class ClientProfileController {
    private final ClientProfileService clientService;

    @Autowired
    public ClientProfileController(ClientProfileService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id) {
        Optional<ClientDTO> clientDTO = clientService.getClient(id);
        return clientDTO.map(client -> new ResponseEntity<ClientDTO>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<ClientDTO>(HttpStatus.NOT_FOUND));
    }
}
