package com.ELOUSTA.ELOUSTA.backend.service.home.filter;


import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;

import java.util.ArrayList;

public class technicianNullFilter implements ITechFilter {
    @Override
    public ArrayList<Technician> Filter(final String query, ArrayList<Technician> technicians) {   //Null object pattern
        if (technicians==null)
            return new ArrayList<>();
        return technicians;
    }
}
