package Services;

import Classes.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SearchPackage.*;

import java.util.List;

@Service
public class searchTechnicianService {

    private final technicianSearch technicianSearch;

    @Autowired
    public searchTechnicianService(SearchPackage.technicianSearch technicianSearch) {
        this.technicianSearch = technicianSearch;
    }
    List<Technician> searchTechnician(String searchQuery)
    {
        //Todo:Fetch from DataBase the technicians
        //Todo:Make The TechnicianEntity a Technician
        //Todo:search Technicians
        //Todo:Return DTO
        return this.technicianSearch.JaroSearch(searchQuery,null);
    }
}
