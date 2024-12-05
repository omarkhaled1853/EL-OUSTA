package com.ELOUSTA.ELOUSTA.backend.controller.home;


import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.service.home.impl.FilterTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.service.home.impl.SearchTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.service.home.impl.SortTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.HomePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserHomePageController {
    private FilterTechnicianService filterService;
    private SearchTechnicianService searchService;
    private SortTechnicianService sortService;

    @Autowired
    public UserHomePageController(FilterTechnicianService filterService, SearchTechnicianService searchService,
                                  SortTechnicianService sortService) {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
    }

    @GetMapping("/")
    public List<TechnicianDTO> startPage() throws IOException {
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
    public List<TechnicianDTO>searchTechnicians(@RequestBody String query) throws IOException {
        return searchService.searchTechnician(query);
    }

    @PostMapping("/sort")
    public List<TechnicianDTO>sortTechnicians(@RequestBody String field) throws IOException {
        return sortService.sortTechnicians(field);
    }

    @PostMapping("/filter")
    public List<TechnicianDTO>filterTechnicians(@RequestBody HomePayload payload) throws IOException {
        return filterService.filterTechnician(payload.getField(), payload.getQuery());
    }
}
