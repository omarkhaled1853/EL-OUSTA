package sortpackage;

import classes.Technician;
import interfaces.ITechSort;
import lombok.Getter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
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
