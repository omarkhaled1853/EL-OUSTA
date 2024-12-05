package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

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
                    experienceSort.setAscending(false);
                }
                return experienceSort;

            case "rate":
                if (rateSort == null) {
                    rateSort = new technicianRateSort();
                    rateSort.setAscending(false);
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
