package com.ELOUSTA.ELOUSTA.backend.controller.home;


import com.example.Backend.classes.HomePayload;
import com.example.Backend.classes.TechnicianDTO;
import com.example.Backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class userHomePageController {
    private filterTechnicianService filterService;
    private searchTechnicianService searchService;
    private sortTechnicianService sortService;

    @Autowired
    public userHomePageController(filterTechnicianService filterService, searchTechnicianService searchService, sortTechnicianService sortService) {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
    }

    @GetMapping("/")
    public List<TechnicianDTO> startPage()
    {
         int counter=0;
         List<TechnicianDTO>DTOs=sortService.sortTechnicians("rate");
         List<TechnicianDTO>toBeReturned=new ArrayList<>();
        for (TechnicianDTO dto:DTOs) {
            counter++;
            if (counter==21)
                break;
            toBeReturned.add(dto);
        }
         return toBeReturned;
    }
    @PostMapping("/search")
    public List<TechnicianDTO>searchTechnicians(@RequestBody String query)
    {
        return searchService.searchTechnician(query);
    }
    @PostMapping("/sort")
    public List<TechnicianDTO>sortTechnicians(@RequestBody String field)
    {
        return sortService.sortTechnicians(field);
    }
    @PostMapping("/filter")
    public List<TechnicianDTO>filterTechnicians(@RequestBody HomePayload payload)
    {
        return filterService.filterTechnician(payload.getField(), payload.getQuery());
    }
}
