package com.ELOUSTA.ELOUSTA.backend.controller.Admin;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminDashBoardDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.AdminHomeDto;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.notification.NotificationService;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/admin/home")
public class HomeController {
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
                homedto.setTechName(technician.getFirstName()); // Adjust based on what `Techname` should represent
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
    @GetMapping("/dash_board")
    public AdminDashBoardDTO getdashboarddata()
    {
        int clients = requestService.clientnumbers();
        int techs = requestService.technumbers();
        int complains = requestService.complainsnumbers();
        int requests = requestService.requestnumbers();
        AdminDashBoardDTO result = new AdminDashBoardDTO();
        result.setNumofclients(clients);
        result.setNumoftechs(techs);
        result.setNumofcomplains(complains);
        result.setNumofrequests(requests);
        return result;
    }
}
