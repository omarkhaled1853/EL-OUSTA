package com.ELOUSTA.ELOUSTA.backend.controller.Complaints;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminComplaintDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.AdminEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.ComplaintEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.AdminRepository;
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
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/get_complains")
    public List<AdminComplaintDTO> getAllComplaints() {
        return complaintRepository.findAll().stream()
                .map(complaint -> {
                    Integer clientId = complaint. getClientEntity() != null ? complaint.getClientEntity().getId() : null;
                    Integer technicianId = complaint.getTechnicianEntity() != null ? complaint.getTechnicianEntity().getId() : null;
                    String clientName = complaint.getClientEntity() != null
                            ? complaint.getClientEntity().getFirstName() + " " + complaint.getClientEntity().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTechnicianEntity() != null
                            ? complaint.getTechnicianEntity().getFirstName() + " " + complaint.getTechnicianEntity().getLastName()
                            : "Unknown Technician";
                    return new AdminComplaintDTO(
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
    public ResponseEntity<AdminComplaintDTO> getComplaintById(@PathVariable int id) {
        return complaintRepository.findById(id)
                .map(complaint -> {
                    Integer clientId = complaint.getClientEntity() != null ? complaint.getClientEntity().getId() : null;
                    Integer technicianId = complaint.getTechnicianEntity() != null ? complaint.getTechnicianEntity().getId() : null;
                    String clientName = complaint.getClientEntity() != null
                            ? complaint.getClientEntity().getFirstName() + " " + complaint.getClientEntity().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTechnicianEntity() != null
                            ? complaint.getTechnicianEntity().getFirstName() + " " + complaint.getTechnicianEntity().getLastName()
                            : "Unknown Technician";
                    AdminComplaintDTO dto = new AdminComplaintDTO(
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
    public ResponseEntity<Void> deleteComplaint(@PathVariable int id, @RequestParam int adminId) {
        // Check if admin has permission to access complaints
        AdminEntity admin = adminRepository.findById(adminId).orElse(null);
        if (admin == null || !admin.isCanAccessComplaints()) {
            return ResponseEntity.status(403).build();
        }

        // Proceed with deletion if complaint exists
        if (complaintRepository.existsById(id)) {
            complaintRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/remove-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId, @RequestParam int adminId) {
        AdminEntity admin = adminRepository.findById(adminId).orElse(null);
        if (admin == null || !admin.isCanAccessComplaints()) {
            return ResponseEntity.status(403).build();
        }
        // Check if the user exists
        if (clientRepository.existsById(userId)) {
            // Delete complaints associated with this user
            List<ComplaintEntity> complaints = complaintRepository.findByClientEntityId(userId);
            complaintRepository.deleteAll(complaints);

            // Delete the user
            clientRepository.deleteById(userId);
            return ResponseEntity.ok("User and associated complaints deleted successfully.");
        }
        return ResponseEntity.status(404).body("User not found.");
    }

    @DeleteMapping("/remove-tech/{techId}")
    public ResponseEntity<String> deleteTechnician(@PathVariable Integer techId, @RequestParam int adminId) {
        AdminEntity admin = adminRepository.findById(adminId).orElse(null);
        if (admin == null || !admin.isCanAccessComplaints()) {
            return ResponseEntity.status(403).build();
        }
        // Check if the technician exists
        if (technicianRepository.existsById(techId)) {
            // Delete complaints associated with this technician
            List<ComplaintEntity> complaints = complaintRepository.findByTechnicianEntityId(techId);
            complaintRepository.deleteAll(complaints);

            // Delete the technician
            technicianRepository.deleteById(techId);
            return ResponseEntity.ok("Technician and associated complaints deleted successfully.");
        }
        return ResponseEntity.status(404).body("Technician not found.");
    }
    @GetMapping("/names/{id}")
    public ResponseEntity<Map<String, String>> getClientAndTechnicianNames(@PathVariable int id) {
        return complaintRepository.findById(id)
                .map(complaint -> {
                    String clientName = complaint.getClientEntity() != null
                            ? complaint.getClientEntity().getFirstName() + " " + complaint.getClientEntity().getLastName()
                            : "Unknown Client";
                    String technicianName = complaint.getTechnicianEntity() != null
                            ? complaint.getTechnicianEntity().getFirstName() + " " + complaint.getTechnicianEntity().getLastName()
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
