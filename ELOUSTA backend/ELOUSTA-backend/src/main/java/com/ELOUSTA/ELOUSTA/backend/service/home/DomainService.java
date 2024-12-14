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

}
