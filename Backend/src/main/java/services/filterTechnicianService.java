package services;

import classes.TechnicianDTO;
import interfaces.ITechFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import filterpackage.*;
import repositories.technicianRepository;

import java.util.List;

@Service
public class filterTechnicianService {
    private final filterCriteriaFactory filterCriteriaFactory;
    private final technicianRepository repository;

    @Autowired
    public filterTechnicianService(filterpackage.filterCriteriaFactory filterCriteriaFactory, technicianRepository repository) {
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
