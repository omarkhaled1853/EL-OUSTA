package com.ELOUSTA.ELOUSTA.backend.service.RequestsFiltering;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StartDateFilter implements IRequestFilter{

        @Override
        public List<RequestEntity> Filter(Date date, List<RequestEntity> data) {
            List<RequestEntity>filteredList = new ArrayList<>();
            for (RequestEntity request:data) {
                if(request.getStartDate().equals(date))
                    filteredList.add(request);
            }
            return filteredList;
        }

}
