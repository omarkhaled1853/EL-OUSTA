package com.ELOUSTA.ELOUSTA.backend.service.home.impl;

import com.example.Backend.classes.Technician;
import com.example.Backend.classes.TechnicianDTO;
import com.example.Backend.classes.technicianMapper;
import com.example.Backend.entities.technicianEntity;
import com.example.Backend.interfaces.ITechSort;
import com.example.Backend.repositories.technicianRepository;
import com.example.Backend.sortpackage.sortStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class sortTechnicianService {

    private final sortStrategyFactory sortStrategyFactory;
    private final technicianMapper mapper;
    private final technicianRepository repository;

    @Autowired
    public sortTechnicianService(com.example.Backend.sortpackage.sortStrategyFactory sortStrategyFactory, technicianMapper technicianMapper, technicianRepository repository) {
        this.sortStrategyFactory = sortStrategyFactory;
        this.mapper=technicianMapper;
        this.repository = repository;
    }

    public List<TechnicianDTO> sortTechnicians(String field)
    {

        List<technicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician> technicians=new ArrayList<>();
        for (technicianEntity entity:dataBaseTechnicians) {
            technicians.add(mapper.technicianEntityToTechnician(entity));
        }
        ITechSort iTechSort =this.sortStrategyFactory.getInstance(field);
        List<Technician> sorted =iTechSort.sort(technicians);
        List<TechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech: sorted) {
            DTOs.add(mapper.technicainToTechnicianDTO(tech));
        }
        return DTOs;
    }
}
