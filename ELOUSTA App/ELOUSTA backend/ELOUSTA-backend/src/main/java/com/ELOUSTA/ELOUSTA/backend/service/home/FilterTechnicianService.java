package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.service.home.filter.FilterCriteriaFactory;
import com.ELOUSTA.ELOUSTA.backend.model.Technician;
import com.ELOUSTA.ELOUSTA.backend.utils.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterTechnicianService {
    private final FilterCriteriaFactory filterCriteriaFactory;
    private final TechnicianRepository repository;

    @Autowired
    public FilterTechnicianService(FilterCriteriaFactory filterCriteriaFactory, TechnicianRepository repository) {
        this.filterCriteriaFactory = filterCriteriaFactory;
        this.repository = repository;
    }

    public List<HomeTechnicianDTO> filterTechnician (String filterType, String filterQuery) throws IOException {
        List<TechnicianEntity>dataBaseTechnicians=this.repository.findAll();
        ArrayList<Technician>technicians=new ArrayList<>();
        for (TechnicianEntity entity:dataBaseTechnicians) {
            technicians.add(TechnicianMapper.technicianEntityToTechnician(entity));
        }
        ITechFilter iTechFilter=this.filterCriteriaFactory.getInstance(filterType);
        List<Technician>filtered=iTechFilter.Filter(filterQuery,technicians);
        List<HomeTechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech:filtered) {
            DTOs.add(TechnicianMapper.technicainToHomeTechnicianDTO(tech));
        }
        return DTOs;
    }

    public List<HomeTechnicianDTO> filterTechniciansOfASpecificProfession(String filterType,String filterQuery,int domainId) throws IOException {
        List<TechnicianEntity>dataBaseTechnicians=this.repository.findTechniciansByDomain(domainId);
        ArrayList<Technician>technicians=new ArrayList<>();
        for (TechnicianEntity entity:dataBaseTechnicians) {
            technicians.add(TechnicianMapper.technicianEntityToTechnician(entity));
        }
        ITechFilter iTechFilter=this.filterCriteriaFactory.getInstance(filterType);
        List<Technician>filtered=iTechFilter.Filter(filterQuery,technicians);
        List<HomeTechnicianDTO>DTOs=new ArrayList<>();
        for (Technician tech:filtered) {
            DTOs.add(TechnicianMapper.technicainToHomeTechnicianDTO(tech));
        }
        return DTOs;
    }
}
