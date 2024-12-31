package com.ELOUSTA.ELOUSTA.backend.service.requestservice;

import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import com.ELOUSTA.ELOUSTA.backend.entity.TechnicianEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.ClientRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.repository.RequestRepo;
import com.ELOUSTA.ELOUSTA.backend.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    ClientRepository clientRepository;
    public RequestEntity Saverequest(RequestEntity request) {
        return requestRepo.save(request);
    }


    /// requesters of admin home ///////////////////
    public List<RequestEntity> getRequestService(String state){
        return requestRepo.getRequests(state);
    }
    public  List<DomainEntity> getProfessions()
    {
        return domainRepository.getall();
    }

    public DomainEntity saveProfession(DomainEntity profession)
    {
        return domainRepository.save(profession);
    }
    public List<TechnicianEntity> getprofessiontech()
    {
        return  technicianRepository.findAll();
    }
    public void deletetech(int id)
    {
        technicianRepository.deleteById(id);
    }
    public int technumbers()
    {
        return technicianRepository.findAll().size();
    }
    public int clientnumbers()
    {
        return clientRepository.findAll().size();
    }
    public int complainsnumbers()
    {
        return 62;
//        return comlainRepository.findAll().size();
    }
    public int requestnumbers()
    {
        return requestRepo.findAll().size();
    }



}
