package com.example.Backend.sortpackage;

import com.example.Backend.classes.Technician;
import com.example.Backend.interfaces.ITechSort;

import java.util.ArrayList;
import java.util.List;

public class technicianNullSort implements ITechSort {
    @Override
    public List<Technician> sort(List<Technician> technicians) {  //An implementation of null object Design pattern
        if (technicians==null)
            return new ArrayList<>();
        return technicians;
    }
}
