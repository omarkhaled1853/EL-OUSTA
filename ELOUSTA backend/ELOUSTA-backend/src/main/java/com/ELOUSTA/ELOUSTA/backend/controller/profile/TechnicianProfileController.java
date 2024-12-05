package com.ELOUSTA.ELOUSTA.backend.controller.profile;

import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.service.profile.TechnicianProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/technician")
public class TechnicianProfileController {
    private final TechnicianProfileService technicianProfileService;

    @Autowired
    public TechnicianProfileController(TechnicianProfileService technicianProfileService) {
        this.technicianProfileService = technicianProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianDTO> getTechnician(@PathVariable Integer id) {
        Optional<TechnicianDTO> clientDTO = technicianProfileService.getTechnician(id);
        return clientDTO.map(technician -> new ResponseEntity<TechnicianDTO>(technician, HttpStatus.OK))
                .orElse(new ResponseEntity<TechnicianDTO>(HttpStatus.NOT_FOUND));
    }
}
