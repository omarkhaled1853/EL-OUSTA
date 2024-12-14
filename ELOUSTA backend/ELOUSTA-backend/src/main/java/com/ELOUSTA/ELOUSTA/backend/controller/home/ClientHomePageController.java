package com.ELOUSTA.ELOUSTA.backend.controller.home;


import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.service.home.FilterTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.service.home.SearchTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.service.home.SortTechnicianService;
import com.ELOUSTA.ELOUSTA.backend.model.HomePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client/home")
public class ClientHomePageController {
    private final FilterTechnicianService filterService;
    private final SearchTechnicianService searchService;
    private final SortTechnicianService sortService;

    @Autowired
    public ClientHomePageController(FilterTechnicianService filterService, SearchTechnicianService searchService,
                                    SortTechnicianService sortService) {
        this.filterService = filterService;
        this.searchService = searchService;
        this.sortService = sortService;
    }

    @GetMapping("/")      //Domains don't support search, sort ,filter
    public List<DomainDTO> startPage() throws IOException {

        return null;
    }

    @PostMapping("/search")
    public List<HomeTechnicianDTO>searchTechnicians(@RequestBody String query) throws IOException {
        return searchService.searchTechnician(query);
    }

    @PostMapping("/search/{id}")
    public List<HomeTechnicianDTO>searchTechniciansWithSpecificDomain(@RequestBody String query,@PathVariable int id) throws IOException {
        return searchService.searchTechniciansOfSpecificProfession(query,id);
    }

    @PostMapping("/sort")
    public List<HomeTechnicianDTO>sortTechnicians(@RequestBody String field) throws IOException {
        return sortService.sortTechnicians(field);
    }

    @PostMapping("/sort/{id}")
    public List<HomeTechnicianDTO>sortTechniciansWithSpecificDomain(@RequestBody String field,@PathVariable int id) throws IOException {
        return sortService.sortTechniciansOfASpecificProfession(field,id);
    }


    @PostMapping("/filter")
    public List<HomeTechnicianDTO>filterTechnicians(@RequestBody HomePayload payload) throws IOException {
        return filterService.filterTechnician(payload.getField(), payload.getQuery());
    }
    @PostMapping("/filter/{id}")
    public List<HomeTechnicianDTO>filterTechnicians(@RequestBody HomePayload payload,@PathVariable int id) throws IOException {
        return filterService.filterTechniciansOfASpecificProfession(payload.getField(), payload.getQuery(), id);
    }
}
