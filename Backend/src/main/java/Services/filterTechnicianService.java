package Services;

import Classes.TechnicianDTO;
import Interfaces.ITechFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FilterPackage.*;

import java.util.List;

@Service
public class filterTechnicianService {
    private final filterCriteriaFactory filterCriteriaFactory;

    @Autowired
    public filterTechnicianService(FilterPackage.filterCriteriaFactory filterCriteriaFactory) {
        this.filterCriteriaFactory = filterCriteriaFactory;
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
