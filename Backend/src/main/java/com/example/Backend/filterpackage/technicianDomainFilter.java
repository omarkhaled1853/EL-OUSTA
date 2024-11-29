package com.example.Backend.filterpackage;

import com.example.Backend.classes.Technician;
import com.example.Backend.interfaces.ITechFilter;

import java.util.ArrayList;

public class technicianDomainFilter implements ITechFilter {


    @Override
    public ArrayList<Technician> Filter(final String query ,final ArrayList<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        ArrayList<Technician>Answer=new ArrayList<>();
        for (Technician tech :technicians) {
            if(tech.getDomain().equals(query))
                Answer.add(tech);
        }
        return Answer;
    }
}
