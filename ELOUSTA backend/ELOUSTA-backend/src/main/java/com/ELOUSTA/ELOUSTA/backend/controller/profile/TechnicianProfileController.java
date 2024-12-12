package com.ELOUSTA.ELOUSTA.backend.controller.profile;

import com.ELOUSTA.ELOUSTA.backend.dto.profileDto.TechnicianProfileProfileDTO;
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
    public ResponseEntity<TechnicianProfileProfileDTO> getTechnician(@PathVariable Integer id) {
        Optional<TechnicianProfileProfileDTO> clientDTO = technicianProfileService.getTechnician(id);
        return clientDTO.map(technician -> new ResponseEntity<TechnicianProfileProfileDTO>(technician, HttpStatus.OK))
                .orElse(new ResponseEntity<TechnicianProfileProfileDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/profile_picture/{id}")
    public ResponseEntity<?> removeClientProfilePicture(@PathVariable Integer id) {
        technicianProfileService.removeTechnicianProfilePhoto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
