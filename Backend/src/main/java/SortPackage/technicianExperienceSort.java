package SortPackage;

import Classes.Technician;
import Interfaces.ITechSort;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
public class technicianExperienceSort implements ITechSort {

    private boolean Ascending;
    @Override
    public List<Technician> sort(List<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        Collections.sort(technicians, (e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate()));
        if(!this.isAscending()) {
            Collections.reverse(technicians);
        }
        return technicians;
    }
}
