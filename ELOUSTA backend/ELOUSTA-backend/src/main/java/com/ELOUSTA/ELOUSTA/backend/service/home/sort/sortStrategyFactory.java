package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechSort;
import org.springframework.stereotype.Service;

@Service
public class sortStrategyFactory {
    private technicianExperienceSort experienceSort=null;
    private technicianRateSort rateSort=null;
    private technicianNullSort nullSort=null;
    public synchronized ITechSort getInstance(String field)
    {
        field = field.toLowerCase();  //ensuring case-Insensitive

        return switch (field) {
            case "experience" -> {
                if (experienceSort == null) {
                    experienceSort = new technicianExperienceSort();
                    experienceSort.setAscending(false);
                }
                yield experienceSort;
            }
            case "rate" -> {
                if (rateSort == null) {
                    rateSort = new technicianRateSort();
                    rateSort.setAscending(false);
                }
                yield rateSort;
            }
            default -> {
                if (nullSort == null) {
                    nullSort = new technicianNullSort();
                }
                yield nullSort;
            }
        };

    }
}
