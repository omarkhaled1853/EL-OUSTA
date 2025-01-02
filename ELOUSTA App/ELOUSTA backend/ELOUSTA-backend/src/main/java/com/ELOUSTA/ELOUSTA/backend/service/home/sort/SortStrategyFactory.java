package com.ELOUSTA.ELOUSTA.backend.service.home.sort;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechSort;
import org.springframework.stereotype.Service;

@Service
public class SortStrategyFactory {
    private TechnicianExperienceSort experienceSort=null;
    private TechnicianRateSort rateSort=null;
    private TechnicianNullSort nullSort=null;
    public synchronized ITechSort getInstance(String field)
    {
        field = field.toLowerCase();  //ensuring case-Insensitive

        return switch (field) {
            case "experience" -> {
                if (experienceSort == null) {
                    experienceSort = new TechnicianExperienceSort();
                    experienceSort.setAscending(false);
                }
                yield experienceSort;
            }
            case "rate" -> {
                if (rateSort == null) {
                    rateSort = new TechnicianRateSort();
                    rateSort.setAscending(false);
                }
                yield rateSort;
            }
            default -> {
                if (nullSort == null) {
                    nullSort = new TechnicianNullSort();
                }
                yield nullSort;
            }
        };

    }
}
