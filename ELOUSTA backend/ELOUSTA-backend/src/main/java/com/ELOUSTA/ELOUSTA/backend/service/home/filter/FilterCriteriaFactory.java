package com.ELOUSTA.ELOUSTA.backend.service.home.filter;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import org.springframework.stereotype.Service;

@Service
public class FilterCriteriaFactory {

    private TechnicianRateFilter rateFilter=null;
    private TechnicianGovernorateFilter governorateFilter=null;
    private TechnicianDomainFilter domainFilter=null;
    private TechnicianDistrictFilter districtFilter=null;
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
            case "district" -> {
                if (districtFilter == null)
                    districtFilter = new TechnicianDistrictFilter();
                return districtFilter;
            }
            case "governorate" -> {
                if (governorateFilter == null)
                    governorateFilter = new TechnicianGovernorateFilter();
                return governorateFilter;
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
