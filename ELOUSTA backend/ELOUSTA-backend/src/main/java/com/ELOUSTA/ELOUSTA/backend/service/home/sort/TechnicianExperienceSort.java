package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechSort;
import com.ELOUSTA.ELOUSTA.backend.service.home.model.Technician;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class TechnicianExperienceSort implements ITechSort {

    private boolean Ascending;
    @Override
    public List<Technician> sort(List<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        technicians.sort((e1, e2) -> e2.getStartDate().compareTo(e1.getStartDate()));
        if(!this.isAscending()) {
            Collections.reverse(technicians);
        }
        return technicians;
    }
}
