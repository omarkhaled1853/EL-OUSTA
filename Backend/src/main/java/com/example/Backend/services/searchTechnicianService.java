package com.example.Backend.services;

import com.example.Backend.classes.Technician;
import com.example.Backend.searchpackage.technicianSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Backend.searchpackage.*;

import java.util.List;

@Service
public class searchTechnicianService {

    private final technicianSearch technicianSearch;

    @Autowired
    public searchTechnicianService(com.example.Backend.searchpackage.technicianSearch technicianSearch) {
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
