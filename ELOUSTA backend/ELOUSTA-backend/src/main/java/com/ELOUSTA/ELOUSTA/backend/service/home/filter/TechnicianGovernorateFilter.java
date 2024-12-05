package com.ELOUSTA.ELOUSTA.backend.service.home.filter;


import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;

import java.util.ArrayList;

public class TechnicianGovernorateFilter implements ITechFilter {



    @Override
    public ArrayList<Technician> Filter(final String query, final ArrayList<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        ArrayList<Technician>Answer=new ArrayList<>();
        for (Technician tech :technicians) {
            if(tech.getLocation().equals(query))
                Answer.add(tech);
        }
        return Answer;
    }
}
