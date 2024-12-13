package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity,Integer> {

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY START_DATE", nativeQuery = true)
    List<RequestEntity> sortRequestsByStartDate(@Param("techId") int techId,@Param("state")String state);


    @Query(value="SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY END_DATE", nativeQuery = true)
    List<RequestEntity> sortRequestsByEndDate(@Param("techId") int techId,@Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE = :state AND LOCATION LIKE CONCAT('%', :filterTerm, '%')", nativeQuery = true)
    List<RequestEntity> FilterRequestsByLocation(@Param("techId") int techId, @Param("state") String state, @Param("filterTerm") String filterTerm);


}
