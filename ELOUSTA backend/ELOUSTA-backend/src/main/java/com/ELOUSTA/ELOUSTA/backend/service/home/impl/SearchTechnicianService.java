package com.ELOUSTA.ELOUSTA.backend.service.home.impl;

import com.example.Backend.classes.Technician;
import com.example.Backend.classes.TechnicianDTO;
import com.example.Backend.classes.technicianMapper;
import com.example.Backend.entities.technicianEntity;
import com.example.Backend.repositories.technicianRepository;
import com.example.Backend.searchpackage.technicianSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchTechnicianService {

    private final technicianSearch techSearch;
    private final technicianMapper mapper;
    private final technicianRepository repository;

    @Autowired
    public SearchTechnicianService(com.example.Backend.searchpackage.technicianSearch technicianSearch, technicianMapper technicianMappper, technicianRepository repository) {
        this.techSearch = technicianSearch;
        this.mapper = technicianMappper;
        this.repository = repository;
    }
    public List<TechnicianDTO> searchTechnician(String searchQuery)
    {
        List<technicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician> technicians=new ArrayList<>();
        for (technicianEntity entity:dataBaseTechnicians) {
            technicians.add(mapper.technicianEntityToTechnician(entity));
        }
        List<Technician> searchedList = techSearch.JaroSearch(searchQuery,technicians);
        List<TechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech: searchedList) {
            DTOs.add(mapper.technicainToTechnicianDTO(tech));
        }
        return DTOs;

    }
}
