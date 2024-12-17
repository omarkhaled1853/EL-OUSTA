package com.ELOUSTA.ELOUSTA.backend.service.home.filter;


import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import com.ELOUSTA.ELOUSTA.backend.model.Technician;

import java.util.ArrayList;

public class TechnicianNullFilter implements ITechFilter {
    @Override
    public ArrayList<Technician> Filter(final String query, ArrayList<Technician> technicians) {   //Null object pattern
        if (technicians==null)
            return new ArrayList<>();
        return technicians;
    }
}
