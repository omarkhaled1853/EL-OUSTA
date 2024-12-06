package com.ELOUSTA.ELOUSTA.backend.service.home.filter;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import org.springframework.stereotype.Service;

@Service
public class FilterCriteriaFactory {

    private TechnicianRateFilter rateFilter=null;
    private TechnicianCityFilter cityFilter =null;
    private TechnicianDomainFilter domainFilter=null;

    private TechnicianNullFilter nullFilter=null;

    public synchronized ITechFilter getInstance(String type)
    {
        type=type.toLowerCase();    //ensure insensitivity
        switch (type) {
            case "rate" -> {
                if (rateFilter == null)
                    rateFilter = new TechnicianRateFilter();
                return rateFilter;
            }
            case "city" -> {
                if (cityFilter == null)
                    cityFilter = new TechnicianCityFilter();
                return cityFilter;
            }
            case "domain" -> {
                if (domainFilter == null)
                    domainFilter = new TechnicianDomainFilter();
                return domainFilter;
            }
            default -> {
                if (nullFilter == null)
                    nullFilter = new TechnicianNullFilter();
                return nullFilter;
            }
        }
    }
}
