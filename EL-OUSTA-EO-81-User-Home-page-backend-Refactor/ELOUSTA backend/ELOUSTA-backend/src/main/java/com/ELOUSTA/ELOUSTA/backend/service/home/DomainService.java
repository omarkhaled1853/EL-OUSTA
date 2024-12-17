package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.ELOUSTA.ELOUSTA.backend.dto.DomainDTO;
import com.ELOUSTA.ELOUSTA.backend.entity.DomainEntity;
import com.ELOUSTA.ELOUSTA.backend.repository.DomainRepository;
import com.ELOUSTA.ELOUSTA.backend.utils.TechnicianMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DomainService {

    @Autowired
    private DomainRepository repository;

    public List<DomainDTO>getDomains() throws IOException {
        List<DomainEntity>domainEntities=repository.findAll();
        List<DomainDTO>answer=new ArrayList<>();
        for (DomainEntity entity:domainEntities) {
            answer.add(TechnicianMapper.domainEntityToDomainDto(entity));
        }

        return answer;
    }
    // Fetch domain by profession name
    public DomainDTO getIDWithName(String professionname) throws IOException {
        List<DomainEntity> domainEntities = repository.getDomains(professionname);
        if (domainEntities.isEmpty()) {
            throw new RuntimeException("Domain not found with name: " + professionname);
        }
        return TechnicianMapper.domainEntityToDomainDto(domainEntities.get(0));
    }

}
