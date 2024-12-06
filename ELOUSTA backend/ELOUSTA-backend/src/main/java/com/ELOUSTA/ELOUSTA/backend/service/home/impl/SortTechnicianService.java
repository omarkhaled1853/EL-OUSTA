package com.ELOUSTA.ELOUSTA.backend.service.home.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.ITechSort;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;
import com.ELOUSTA.ELOUSTA.backend.service.home.sort.SortStrategyFactory;
import com.ELOUSTA.ELOUSTA.backend.utils.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SortTechnicianService {

    private final SortStrategyFactory sortStrategyFactory;
    private final TechnicianRepository repository;

    @Autowired
    public SortTechnicianService(SortStrategyFactory sortStrategyFactory, TechnicianRepository repository) {
        this.sortStrategyFactory = sortStrategyFactory;
        this.repository = repository;
    }

    public List<HomeTechnicianDTO> sortTechnicians(String field) throws IOException {

        List<TechnicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician> technicians=new ArrayList<>();
        for (TechnicianEntity entity:dataBaseTechnicians) {
            technicians.add(TechnicianMapper.technicianEntityToTechnician(entity));
        }
        ITechSort iTechSort =this.sortStrategyFactory.getInstance(field);
        List<Technician> sorted =iTechSort.sort(technicians);
        List<HomeTechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech: sorted) {
            DTOs.add(TechnicianMapper.technicainToHomeTechnicianDTO(tech));
        }
        return DTOs;
    }
}
