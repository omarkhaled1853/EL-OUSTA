package com.ELOUSTA.ELOUSTA.backend.controller.Admin;


import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.dto.AdminHomeDto;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client/request")
public class AdminRequestController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TechnicianRepository technicianRepository;

    @GetMapping("/Done_Requests/{state}")
    public List<AdminHomeDto> getRequestWithState(@PathVariable String state) {
        List<RequestEntity> requests = requestService.getRequestService(state);
        List<AdminHomeDto> result = new ArrayList<>();

        for (RequestEntity r : requests) {
            AdminHomeDto homedto = new AdminHomeDto(); // Create a new DTO instance for each request
            technicianRepository.findById(r.getTechId()).ifPresent(technician -> {
                homedto.setLocation(r.getLocation());
                homedto.setTechName(technician.getFirstName() +" "+ technician.getLastName()); // Adjust based on what `Techname` should represent
                homedto.setDate(r.getEndDate());
                homedto.setDescription(r.getDescription());
                result.add(homedto);
            });
        }
        return result;
    }
    @GetMapping("/photos")
    public List<String> getPhotosLocation()
    {
        List<DomainEntity> domains = requestService.getProfessions();
        List<String> result = new ArrayList<>();
        for(DomainEntity profession : domains)
        {
            result.add(profession.getPhoto());

        }
        return result;
    }
}
