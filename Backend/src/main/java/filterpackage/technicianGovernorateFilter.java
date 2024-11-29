package filterpackage;

import classes.Technician;
import interfaces.ITechFilter;

import java.util.ArrayList;

public class technicianGovernorateFilter implements ITechFilter {



    @Override
    public ArrayList<Technician> Filter(final String query,final ArrayList<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        ArrayList<Technician>Answer=new ArrayList<>();
        for (Technician tech :technicians) {
            if(tech.getGovernorate().equals(query))
                Answer.add(tech);
        }
        return Answer;
    }
}
