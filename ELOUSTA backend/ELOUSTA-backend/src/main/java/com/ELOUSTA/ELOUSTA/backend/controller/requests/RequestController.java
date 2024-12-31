package com.ELOUSTA.ELOUSTA.backend.controller.requests;


import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.RequestDto;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.dto.AdminHomeDto;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client/request")
public class RequestController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TechnicianRepository technicianRepository;

    @PostMapping("/addRequest")
    public String addRequest(@RequestBody RequestDto requestDto) {
        try {

            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setUserId(requestDto.getUserid());
            requestEntity.setTechId(requestDto.getTechid()); // Assuming 'techid' is a typo and should be 'techid'
            requestEntity.setStartDate(requestDto.getStartdate());
            requestEntity.setEndDate(requestDto.getEnddate());
            requestEntity.setLocation(requestDto.getLocation());
            requestEntity.setDescription(requestDto.getDescription());
            requestEntity.setState("Pending"); // Default state

            // Save the entity
            requestService.Saverequest(requestEntity);

            String message = requestEntity.getDescription()
                    +"\n" + "Location: " + requestEntity.getLocation()
                    +"\nstart date: " + requestEntity.getStartDate()
                    +"\nend date: " + requestEntity.getEndDate();

            notificationService.sendNotificationToTechnician(message, requestDto.getTechid());

            return "Request successfully saved";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
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
