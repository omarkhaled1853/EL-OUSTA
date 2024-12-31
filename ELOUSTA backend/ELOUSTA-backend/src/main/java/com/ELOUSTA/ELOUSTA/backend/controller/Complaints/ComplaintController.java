package com.ELOUSTA.ELOUSTA.backend.controller.Complaints;

import com.ELOUSTA.ELOUSTA.backend.dto.ComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.ComplaintRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @GetMapping
    public List<ComplaintDTO> getAllComplaints() {
        return complaintRepository.findAll().stream()
                .map(complaint -> {
                    Integer clientId = complaint.getUser() != null ? complaint.getUser().getId() : null;
                    Integer technicianId = complaint.getTech() != null ? complaint.getTech().getId() : null;
                    String clientName = complaint.getUser() != null
                            ? complaint.getUser().getFirstName() + " " + complaint.getUser().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTech() != null
                            ? complaint.getTech().getFirstName() + " " + complaint.getTech().getLastName()
                            : "Unknown Technician";
                    return new ComplaintDTO(
                            complaint.getId(),
                            complaint.getComplaintBody(),
                            complaint.getState(),
                            complaint.getDirection(),
                            clientId,
                            clientName,
                            technicianId,
                            technicianName
                    );
                })
                .toList(); // Convert the stream to a List
    }
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDTO> getComplaintById(@PathVariable String id) {
        return complaintRepository.findById(id)
                .map(complaint -> {
                    Integer clientId = complaint.getUser() != null ? complaint.getUser().getId() : null;
                    Integer technicianId = complaint.getTech() != null ? complaint.getTech().getId() : null;
                    String clientName = complaint.getUser() != null
                            ? complaint.getUser().getFirstName() + " " + complaint.getUser().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTech() != null
                            ? complaint.getTech().getFirstName() + " " + complaint.getTech().getLastName()
                            : "Unknown Technician";
                    ComplaintDTO dto = new ComplaintDTO(
                            complaint.getId(),
                            complaint.getComplaintBody(),
                            complaint.getState(),
                            complaint.getDirection(),
                            clientId,
                            clientName,
                            technicianId,
                            technicianName
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable String id) {
        if (complaintRepository.existsById(id)) {
            complaintRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        // Check if the user exists
        if (clientRepository.existsById(userId)) {
            // Delete complaints associated with this user
            List<ComplaintEntity> complaints = complaintRepository.findByUserId(userId);
            complaintRepository.deleteAll(complaints);

            // Delete the user
            clientRepository.deleteById(userId);
            return ResponseEntity.ok("User and associated complaints deleted successfully.");
        }
        return ResponseEntity.status(404).body("User not found.");
    }

    @DeleteMapping("/remove-tech/{techId}")
    public ResponseEntity<String> deleteTechnician(@PathVariable Integer techId) {
        // Check if the technician exists
        if (technicianRepository.existsById(techId)) {
            // Delete complaints associated with this technician
            List<ComplaintEntity> complaints = complaintRepository.findByTechId(techId);
            complaintRepository.deleteAll(complaints);

            // Delete the technician
            technicianRepository.deleteById(techId);
            return ResponseEntity.ok("Technician and associated complaints deleted successfully.");
        }
        return ResponseEntity.status(404).body("Technician not found.");
    }
    @GetMapping("/names/{id}")
    public ResponseEntity<Map<String, String>> getClientAndTechnicianNames(@PathVariable String id) {
        return complaintRepository.findById(id)
                .map(complaint -> {
                    String clientName = complaint.getUser() != null
                            ? complaint.getUser().getFirstName() + " " + complaint.getUser().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTech() != null
                            ? complaint.getTech().getFirstName() + " " + complaint.getTech().getLastName()
                            : "Unknown Technician";

                    // Create a response map
                    Map<String, String> response = new HashMap<>();
                    response.put("Client", clientName);
                    response.put("Technician", technicianName);

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
