package com.ELOUSTA.ELOUSTA.backend.service.home.impl;

import com.example.Backend.classes.Technician;
import com.example.Backend.classes.TechnicianDTO;
import com.example.Backend.classes.technicianMapper;
import com.example.Backend.entities.technicianEntity;
import com.example.Backend.filterpackage.*;
import com.example.Backend.filterpackage.filterCriteriaFactory;
import com.example.Backend.interfaces.ITechFilter;
import com.example.Backend.repositories.technicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class filterTechnicianService {
    private final filterCriteriaFactory filterCriteriaFactory;
    private final technicianRepository repository;

    private technicianMapper mapper;

    @Autowired
    public filterTechnicianService(filterCriteriaFactory filterCriteriaFactory, technicianRepository repository,technicianMapper technicianMapper) {
        this.filterCriteriaFactory = filterCriteriaFactory;
        this.repository = repository;
        this.mapper=technicianMapper;
    }

    public List<TechnicianDTO> filterTechnician (String filterType, String filterQuery)
    {
        List<technicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician>technicians=new ArrayList<>();
        for (technicianEntity entity:dataBaseTechnicians) {
            technicians.add(mapper.technicianEntityToTechnician(entity));
        }
        ITechFilter iTechFilter=this.filterCriteriaFactory.getInstance(filterType);
        List<Technician>filtered=iTechFilter.Filter(filterQuery,technicians);
        List<TechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech:filtered) {
            DTOs.add(mapper.technicainToTechnicianDTO(tech));
        }
        return DTOs;
    }
}
