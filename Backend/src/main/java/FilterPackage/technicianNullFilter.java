package FilterPackage;

import Classes.Technician;
import Interfaces.ITechFilter;

import java.util.ArrayList;

public class technicianNullFilter implements ITechFilter {
    @Override
    public ArrayList<Technician> Filter(final String query,ArrayList<Technician> technicians) {   //Null object pattern
        if (technicians==null)
            return new ArrayList<>();
        return technicians;
    }
}
