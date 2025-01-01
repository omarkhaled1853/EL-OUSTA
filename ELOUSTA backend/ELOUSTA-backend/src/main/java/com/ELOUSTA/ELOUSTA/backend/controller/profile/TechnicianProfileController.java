package com.ELOUSTA.ELOUSTA.backend.controller.profile;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileDTO;
import com.ELOUSTA.ELOUSTA.backend.service.profile.TechnicianProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tech/profile")
public class TechnicianProfileController {
    private final TechnicianProfileService technicianProfileService;

    @Autowired
    public TechnicianProfileController(TechnicianProfileService technicianProfileService) {
        this.technicianProfileService = technicianProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianProfileDTO> getTechnician(@PathVariable Integer id) {
        Optional<TechnicianProfileDTO> clientDTO = technicianProfileService.getTechnician(id);
        return clientDTO.map(technician -> new ResponseEntity<TechnicianProfileDTO>(technician, HttpStatus.OK))
                .orElse(new ResponseEntity<TechnicianProfileDTO>(HttpStatus.NOT_FOUND));
    }
}
