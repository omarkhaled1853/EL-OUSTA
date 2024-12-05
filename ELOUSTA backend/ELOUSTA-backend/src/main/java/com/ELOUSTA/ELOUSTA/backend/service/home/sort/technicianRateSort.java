package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

import com.example.Backend.classes.Technician;
import com.example.Backend.interfaces.ITechSort;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class technicianRateSort implements ITechSort {

    private boolean Ascending;
    @Override
    public List<Technician> sort(List<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        Collections.sort(technicians, (e1, e2) -> Integer.compare(Integer.parseInt(e1.getRate()), Integer.parseInt(e2.getRate())));
        if(!this.isAscending()) {
            Collections.reverse(technicians);
        }
        return technicians;
    }
}
