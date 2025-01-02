package com.ELOUSTA.ELOUSTA.backend.service.request.client;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class RequestsTestData {
    static List<RequestEntity> testPendingRequestEntityList() {
        return Arrays.asList(
                RequestEntity.builder()
                        .id(1)
                        .userId(1)
                        .techId(101)
                        .state("PENDING")
                        .description("Request 1")
                        .Location("Location A")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build(),
                RequestEntity.builder()
                        .id(2)
                        .userId(1)
                        .techId(102)
                        .state("PENDING")
                        .description("Request 2")
                        .Location("Location B")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()
        );
    }

    static List<RequestEntity> testInProgressRequestEntityList() {
        return Arrays.asList(
                RequestEntity.builder()
                        .id(1)
                        .userId(1)
                        .techId(101)
                        .state("IN-PROGRESS")
                        .description("Request 1")
                        .Location("Location A")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build(),
                RequestEntity.builder()
                        .id(2)
                        .userId(1)
                        .techId(102)
                        .state("IN-PROGRESS")
                        .description("Request 2")
                        .Location("Location B")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()
        );
    }

    static List<RequestEntity> testCompletedRequestEntityList() {
        return Arrays.asList(
                RequestEntity.builder()
                        .id(1)
                        .userId(1)
                        .techId(101)
                        .state("COMPLETED")
                        .description("Request 1")
                        .Location("Location A")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build(),
                RequestEntity.builder()
                        .id(2)
                        .userId(1)
                        .techId(102)
                        .state("COMPLETED")
                        .description("Request 2")
                        .Location("Location B")
                        .startDate(new Date())
                        .endDate(new Date())
                        .build()
        );
    }
}
