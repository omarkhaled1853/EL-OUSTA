package Services;

import Classes.Technician;
import Classes.TechnicianDTO;
import Interfaces.ITechSort;
import SortPackage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class sortTechnicianService {

    private final sortStrategyFactory sortStrategyFactory;

    @Autowired
    public sortTechnicianService(SortPackage.sortStrategyFactory sortStrategyFactory) {
        this.sortStrategyFactory = sortStrategyFactory;
    }

    public List<TechnicianDTO> sortTechnicians(String field, boolean isAscending)
    {

        //Todo:Fetch from DataBase the technicians
        //Todo:Make The TechnicianEntity a Technician
        ITechSort iTechSort=sortStrategyFactory.getInstance(field);
        //Todo:Sort Technicians
        //Todo:Return DTO
        return null;
    }
}
