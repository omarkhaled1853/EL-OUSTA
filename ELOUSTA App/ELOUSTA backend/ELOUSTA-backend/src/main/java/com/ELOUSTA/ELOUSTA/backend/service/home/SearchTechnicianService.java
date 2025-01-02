package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.ELOUSTA.ELOUSTA.backend.dto.ProfessionCardDTO;
import com.ELOUSTA.ELOUSTA.backend.dto.homeDto.HomeTechnicianDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import com.ELOUSTA.ELOUSTA.backend.model.Technician;
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
    public List<ProfessionCardDTO> AdminsearchTechnician(String searchQuery) throws IOException {
        // Fetch all technicians from the database
        List<TechnicianEntity> dataBaseTechnicians = this.repository.findAll();
        ArrayList<Technician> technicians = new ArrayList<>();

        // Map TechnicianEntity to Technician object
        for (TechnicianEntity entity : dataBaseTechnicians) {
            technicians.add(TechnicianMapper.technicianEntityToTechnician(entity));
        }

        // Perform the search
        List<Technician> searchedList = techSearch.JaroSearch(searchQuery, technicians);

        // Create a list of ProfessionCardDTO from searched technicians
        List<ProfessionCardDTO> result = new ArrayList<>();
        for (Technician tech : searchedList) {
            ProfessionCardDTO cardDTO = new ProfessionCardDTO();

            // Map Technician data to ProfessionCardDTO
            cardDTO.setTechId(tech.getId());
            cardDTO.setTechName(tech.getFirstName() + " " + tech.getLastName());
            cardDTO.setEmail(tech.getEmail());
            cardDTO.setProfessionName(tech.getDomainDTO().getName());
            cardDTO.setRate(tech.getRate());
//            cardDTO.setComplainNumber(tech.getComplainCount()); // Assuming you have a method to get the complain count

            result.add(cardDTO);
        }

        return result;
    }


    public List<HomeTechnicianDTO> searchTechniciansOfSpecificProfession(String searchQuery,int domainId) throws IOException {
        List<TechnicianEntity>dataBaseTechnicians=this.repository.findTechniciansByDomain(domainId);
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
