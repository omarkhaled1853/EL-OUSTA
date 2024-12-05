package com.ELOUSTA.ELOUSTA.backend.service.home.filter;

import com.ELOUSTA.ELOUSTA.backend.service.home.ITechFilter;
import org.springframework.stereotype.Service;

@Service
public class filterCriteriaFactory {

    private technicianRateFilter rateFilter=null;
    private technicianGovernorateFilter governorateFilter=null;
    private technicianDomainFilter domainFilter=null;
    private technicianDistrictFilter districtFilter=null;
    private  technicianNullFilter nullFilter=null;

    public synchronized ITechFilter getInstance(String type)
    {
        type=type.toLowerCase();    //ensure insensitivity
        switch (type) {
            case "rate" -> {
                if (rateFilter == null)
                    rateFilter = new technicianRateFilter();
                return rateFilter;
            }
            case "district" -> {
                if (districtFilter == null)
                    districtFilter = new technicianDistrictFilter();
                return districtFilter;
            }
            case "governorate" -> {
                if (governorateFilter == null)
                    governorateFilter = new technicianGovernorateFilter();
                return governorateFilter;
            }
            case "domain" -> {
                if (domainFilter == null)
                    domainFilter = new technicianDomainFilter();
                return domainFilter;
            }
            default -> {
                if (nullFilter == null)
                    nullFilter = new technicianNullFilter();
                return nullFilter;
            }
        }
    }
}
