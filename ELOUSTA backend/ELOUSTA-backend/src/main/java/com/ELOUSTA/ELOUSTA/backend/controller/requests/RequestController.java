package com.ELOUSTA.ELOUSTA.backend.controller.requests;


import com.ELOUSTA.ELOUSTA.backend.dto.requestDto.RequestDto;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.service.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/addRequest")
    public String addRequest(@RequestBody RequestDto requestDto) {
        try {

            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setUserId(requestDto.getUserid());
            requestEntity.setTechId(requestDto.getTechid()); // Assuming 'tachid' is a typo and should be 'techid'
            requestEntity.setStartDate(requestDto.getStartdate());
            requestEntity.setEndDate(requestDto.getEnddate());
            requestEntity.setLocation(requestDto.getLocation());
            requestEntity.setDescription(requestDto.getDescription());
            requestEntity.setState("Pending"); // Default state

            // Save the entity
            requestService.Saverequest(requestEntity);

            return "Request successfully saved";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
