package com.ELOUSTA.Profile_backend.controller;

import com.ELOUSTA.Profile_backend.dto.TechnicianDTO;
import com.ELOUSTA.Profile_backend.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/technician")
public class TechnicianController {
    private final TechnicianService technicianService;

    @Autowired
    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianDTO> getTechnician(@PathVariable Integer id) {
        Optional<TechnicianDTO> clientDTO = technicianService.getTechnician(id);
        return clientDTO.map(technician -> new ResponseEntity<TechnicianDTO>(technician, HttpStatus.OK))
                .orElse(new ResponseEntity<TechnicianDTO>(HttpStatus.NOT_FOUND));
    }
}
