package com.ELOUSTA.ELOUSTA.backend.controller.adminRequests;

import com.ELOUSTA.ELOUSTA.backend.dto.AdminRequestsDTO;
import com.ELOUSTA.ELOUSTA.backend.service.adminRequests.AdminRequestPayload;
import com.ELOUSTA.ELOUSTA.backend.service.adminRequests.AdminRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminRequestsController {
    @Autowired
    private AdminRequestsService service;

    @GetMapping("/admin/requests/{state}")
    List<AdminRequestsDTO> getRequests(@PathVariable String state)
    {
       return this.service.getRequests(state);
    }

    @PostMapping("/admin/requests/search")
    List<AdminRequestsDTO> find(@RequestBody AdminRequestPayload payload)
    {
        return this.service.searchRequests(payload.getTechnicianUserName(),payload.getState());
    }



    
 
}
