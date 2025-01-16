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
@RequestMapping("/tech/profile")
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

    @DeleteMapping("/delete/profile-picture/{id}")
    public ResponseEntity<?> removeTechnicianProfilePicture(@PathVariable Integer id) {
        technicianProfileService.removeTechnicianProfilePicture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/patch/password/{id}")
    public ResponseEntity<?> resetTechnicianPassword(@PathVariable Integer id,
                                                 @RequestBody String newPassword) {
        technicianProfileService.resetTechnicianPassword(id, newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/portfolio/{id}/{portfolioId}")
    public ResponseEntity<?> removeTechnicianPortfolio(@PathVariable Integer id,
                                                       @PathVariable Integer portfolioId) {
        technicianProfileService.removeTechnicianPortfolio(id, portfolioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
