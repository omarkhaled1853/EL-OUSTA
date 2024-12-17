package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechSort;
import com.ELOUSTA.ELOUSTA.backend.model.Technician;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Data
public class TechnicianRateSort implements ITechSort {

    private boolean Ascending;
    @Override
    public List<Technician> sort(List<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        technicians.sort(Comparator.comparingDouble(Technician::getRate));
        if(!this.isAscending()) {
            Collections.reverse(technicians);
        }
        return technicians;
    }
}
