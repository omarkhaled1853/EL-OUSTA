package com.ELOUSTA.ELOUSTA.backend.service.home.search;

import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianSearch {


    public List<Technician>JaroSearch(String Query, List<Technician> searchSpace)
    {
        if (searchSpace==null)      //handle nullability
            return new ArrayList<>();

        String query=Query.toLowerCase();
        double threshold=0.6;
        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();
        return searchSpace.stream()
                .filter(t -> {
                    return jaroWinkler.apply(query, t.getFirstName()) >= threshold
                            ||jaroWinkler.apply(query, t.getLastName() ) >= threshold
                            ||jaroWinkler.apply(query, t.getRate().toString()) >= threshold
                            ||jaroWinkler.apply(query, t.getDomainDTO().getName()) >= threshold
                            ||jaroWinkler.apply(query, t.getLocation()) >= threshold;
                })
                .collect(Collectors.toList());
    }


}
