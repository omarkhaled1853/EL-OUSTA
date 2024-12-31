package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity,Integer> {


    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state", nativeQuery = true)
    List<RequestEntity> getRequestsByState(@Param("techId")int techId,@Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY START_DATE", nativeQuery = true)
    List<RequestEntity> sortRequestsByStartDate(@Param("techId") int techId,@Param("state")String state);


    @Query(value="SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY END_DATE", nativeQuery = true)
    List<RequestEntity> sortRequestsByEndDate(@Param("techId") int techId,@Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE = :state AND LOCATION LIKE CONCAT('%', :filterTerm, '%')", nativeQuery = true)
    List<RequestEntity> filterRequestsByLocation(@Param("techId") int techId, @Param("state") String state, @Param("filterTerm") String filterTerm);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE = :state AND DESCRIPTION LIKE CONCAT('%', :searchTerm, '%')", nativeQuery = true)
    List<RequestEntity> searchRequestsByDescription(@Param("techId") int techId, @Param("state") String state, @Param("searchTerm") String searchTerm);

//    @Query(value = "SELECT * FROM REQUEST WHERE ID= :id AND TECHID= :techId AND USERID = :clientId")


    @Query(value = "SELECT r.* FROM REQUEST r LEFT JOIN TECHNICIAN t ON r.TECHID= t.id WHERE STATE = :state" +
            " AND t.username LIKE CONCAT('%', :technicianUserName, '%')", nativeQuery = true)
    List<RequestEntity> searchByTechnicianUserName(@Param("technicianUserName") String technicianUserName, @Param("state") String state);

    @Query(value = "SELECT * FROM REQUEST WHERE STATE = :state", nativeQuery = true)
    List<RequestEntity> getAllByState(@Param("state")String state);


}
