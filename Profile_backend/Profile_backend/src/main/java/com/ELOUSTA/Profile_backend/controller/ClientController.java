package com.ELOUSTA.Profile_backend.controller;

import com.ELOUSTA.Profile_backend.dto.ClientDTO;
import com.ELOUSTA.Profile_backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Integer id) {
        Optional<ClientDTO> clientDTO = clientService.getClient(id);
        return clientDTO.map(client -> new ResponseEntity<ClientDTO>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<ClientDTO>(HttpStatus.NOT_FOUND));
    }

    @GetMapping({"path"})
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable String path) throws IOException {
        byte[] profilePhoto = clientService.getProfilePhoto(path);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(profilePhoto);
    }
}
