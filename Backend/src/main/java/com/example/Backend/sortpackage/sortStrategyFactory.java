package com.example.Backend.sortpackage;

import com.example.Backend.interfaces.ITechSort;
import org.springframework.stereotype.Service;

@Service
public class sortStrategyFactory {
    private technicianExperienceSort experienceSort=null;
    private technicianRateSort rateSort=null;
    private technicianNullSort nullSort=null;
    public synchronized ITechSort getInstance(String field)
    {
        field = field.toLowerCase();  //ensuring case-Insensitive

        switch (field) {
            case "experience":
                if (experienceSort == null) {
                    experienceSort = new technicianExperienceSort();
                }
                return experienceSort;

            case "rate":
                if (rateSort == null) {
                    rateSort = new technicianRateSort();
                }
                return rateSort;

            default:
                if (nullSort == null) {
                    nullSort = new technicianNullSort();
                }
                return nullSort;
        }

    }
}
