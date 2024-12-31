package com.ELOUSTA.ELOUSTA.backend.controller.admin;

import com.ELOUSTA.ELOUSTA.backend.dto.ProfessionCardDTO;
import com.ELOUSTA.ELOUSTA.backend.service.home.SearchTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/search")
public class SearchController {
    @Autowired
    private SearchTechnicianService searchTechnicianService;
    @GetMapping("/{query}")
    public List<ProfessionCardDTO> findcardwithsearch(@PathVariable String query) throws IOException {
        return searchTechnicianService.AdminsearchTechnician(query);
    }
}
