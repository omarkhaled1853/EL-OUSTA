package com.example.Backend.searchpackage;

import com.example.Backend.classes.Technician;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

@Service
public class technicianSearch {

    public List<Technician>JaroSearch(String Query, List<Technician> searchSpace)
    {
        if (searchSpace==null)      //handle nullability
            return new ArrayList<>();

        String query=Query.toLowerCase();
        double threshold=0.6;
        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();
        return searchSpace.stream()
                .filter(t -> {
                    return jaroWinkler.apply(query, t.getFName()) >= threshold
                            ||jaroWinkler.apply(query, t.getLName() ) >= threshold
                            ||jaroWinkler.apply(query, t.getRate()) >= threshold
                            ||jaroWinkler.apply(query, t.getDomain()) >= threshold
                            ||jaroWinkler.apply(query, t.getGovernorate()) >= threshold
                            ||jaroWinkler.apply(query, t.getDistrict()) >= threshold;
                })
                .collect(Collectors.toList());
    }


}
