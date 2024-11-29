package com.example.Backend.services;

import com.example.Backend.classes.TechnicianDTO;
import com.example.Backend.filterpackage.filterCriteriaFactory;
import com.example.Backend.interfaces.ITechFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Backend.filterpackage.*;
import com.example.Backend.repositories.technicianRepository;

import java.util.List;

@Service
public class filterTechnicianService {
    private final filterCriteriaFactory filterCriteriaFactory;
    private final technicianRepository repository;

    @Autowired
    public filterTechnicianService(filterCriteriaFactory filterCriteriaFactory, technicianRepository repository) {
        this.filterCriteriaFactory = filterCriteriaFactory;
        this.repository = repository;
    }

    public List<TechnicianDTO> filterTechnician (String filterType, String filterQuery)
    {
        //Todo:Fetch from DataBase the technicians
        //Todo:Make The TechnicianEntity a Technician
        ITechFilter iTechFilter=this.filterCriteriaFactory.getInstance(filterType);
        //Todo:filter Technicians
        //Todo:Return DTO
        return null;
    }
}
