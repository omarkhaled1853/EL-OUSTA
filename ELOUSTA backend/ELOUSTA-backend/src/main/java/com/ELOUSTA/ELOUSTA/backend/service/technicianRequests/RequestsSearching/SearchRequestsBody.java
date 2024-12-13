package com.ELOUSTA.ELOUSTA.backend.service.technicianRequests.RequestsSearching;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRequestsBody {
    private final Map<String,List<RequestEntity>> searchRequestsMap =new HashMap<>();
    List<RequestEntity> searchInBody(String query,List<RequestEntity> data)
    {
        buildSearchMap(data);
        return searchRequestsMap.get(query);
    }

    private void buildSearchMap(List<RequestEntity> data) {

        for (RequestEntity entity:data) {
            buildSearchMapByEntity(entity);
        }

    }

    private void buildSearchMapByEntity(RequestEntity entity) {

        String description=entity.getDescription();
        int len=description.length();
        for (int i = 0; i < len; i++) {
            for (int j = i; j <len ; j++) {
                String prefix=description.substring(i,j+1);
                if(searchRequestsMap.containsKey(prefix))
                {
                    if(!searchRequestsMap.get(prefix).contains(entity)) {
                        searchRequestsMap.get(prefix).add(entity);
                    }
                }
                else
                {
                    List<RequestEntity>requestEntities=new ArrayList<>();
                    requestEntities.add(entity);
                    searchRequestsMap.put(prefix,requestEntities);
                }
            }
        }
    }

}
