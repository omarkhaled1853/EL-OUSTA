package com.example.Backend.controllers;


import com.example.Backend.classes.RequestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class technicianHomePageController {

    @GetMapping("/technician/Home")
    public List<RequestDTO> getRequests()
    {
         List<RequestDTO>DTOs=new ArrayList<>();
         DTOs.add(new RequestDTO(5,10,15,null,"On going",new Date(2024,10,15),new Date(2024,11,13)));
         DTOs.add(new RequestDTO(10,10,15,null,"On going",new Date(2024,10,15),new Date(2024,11,13)));
         DTOs.add(new RequestDTO(15,10,15,null,"On going",new Date(2024,10,15),new Date(2024,11,13)));
         DTOs.add(new RequestDTO(20,10,15,null,"On going",new Date(2024,10,15),new Date(2024,11,13)));
         return DTOs;
    }
}