package com.ELOUSTA.ELOUSTA.backend.controller.home;


import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
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
    private final FilterTechnicianService filterService;
    private final SearchTechnicianService searchService;
    private final SortTechnicianService sortService;

    @Autowired
    public UserHomePageController(FilterTechnicianService filterService, SearchTechnicianService searchService,
                                  SortTechnicianService sortService) {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
    }

    @GetMapping("/")
    public List<HomeTechnicianDTO> startPage() throws IOException {
         int counter=0;
         List<HomeTechnicianDTO>DTOs=sortService.sortTechnicians("rate");
         List<HomeTechnicianDTO>toBeReturned=new ArrayList<>();
        for (HomeTechnicianDTO dto:DTOs) {
            counter++;
            if (counter==21)
                break;
            toBeReturned.add(dto);
        }
         return toBeReturned;
    }

    @PostMapping("/search")
    public List<HomeTechnicianDTO>searchTechnicians(@RequestBody String query) throws IOException {
        return searchService.searchTechnician(query);
    }

    @PostMapping("/sort")
    public List<HomeTechnicianDTO>sortTechnicians(@RequestBody String field) throws IOException {
        return sortService.sortTechnicians(field);
    }

    @PostMapping("/filter")
    public List<HomeTechnicianDTO>filterTechnicians(@RequestBody HomePayload payload) throws IOException {
        return filterService.filterTechnician(payload.getField(), payload.getQuery());
    }
}
