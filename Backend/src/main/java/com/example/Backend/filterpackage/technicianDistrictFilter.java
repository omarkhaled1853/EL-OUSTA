package com.example.Backend.filterpackage;

import com.example.Backend.classes.Technician;
import com.example.Backend.interfaces.ITechFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@Setter
@Getter
public class technicianDistrictFilter implements ITechFilter {


    @Override
    public ArrayList<Technician> Filter(final String query,final ArrayList<Technician> technicians) {
        if (technicians==null)
            return new ArrayList<>();
        ArrayList<Technician>Answer=new ArrayList<>();
        for (Technician tech :technicians) {
              if(tech.getDistrict().equals(query))
                  Answer.add(tech);
        }
        return Answer;
    }
}
