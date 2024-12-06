package com.ELOUSTA.ELOUSTA.backend.service.home.impl;

import com.ELOUSTA.ELOUSTA.backend.dto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.TechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;
import com.ELOUSTA.ELOUSTA.backend.service.home.search.TechnicianSearch;
import com.ELOUSTA.ELOUSTA.backend.utils.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchTechnicianService {

    private final TechnicianSearch techSearch;
    private final TechnicianRepository repository;

    @Autowired
    public SearchTechnicianService(TechnicianSearch techSearch, TechnicianRepository repository) {
        this.techSearch = techSearch;
        this.repository = repository;
    }


    public List<HomeTechnicianDTO> searchTechnician(String searchQuery) throws IOException {
        List<TechnicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician> technicians=new ArrayList<>();
        for (TechnicianEntity entity:dataBaseTechnicians) {
            technicians.add(TechnicianMapper.technicianEntityToTechnician(entity));
        }
        List<Technician> searchedList = techSearch.JaroSearch(searchQuery,technicians);
        List<HomeTechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech: searchedList) {
            DTOs.add(TechnicianMapper.technicainToHomeTechnicianDTO(tech));
        }
        return DTOs;

    }
}
