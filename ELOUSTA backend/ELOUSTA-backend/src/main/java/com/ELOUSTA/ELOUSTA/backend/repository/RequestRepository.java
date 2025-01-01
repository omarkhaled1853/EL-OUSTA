package com.ELOUSTA.ELOUSTA.backend.repository;

import com.ELOUSTA.ELOUSTA.backend.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity,Integer> {


    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state", nativeQuery = true)
    List<RequestEntity> getTechnicianRequestsByState(@Param("techId")int techId, @Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY START_DATE", nativeQuery = true)
    List<RequestEntity> sortTechnicianRequestsByStartDate(@Param("techId") int techId, @Param("state")String state);


    @Query(value="SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE= :state ORDER BY END_DATE", nativeQuery = true)
    List<RequestEntity> sortTechnicianRequestsByEndDate(@Param("techId") int techId, @Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE = :state AND LOCATION LIKE CONCAT('%', :filterTerm, '%')", nativeQuery = true)
    List<RequestEntity> filterTechnicianRequestsByLocation(@Param("techId") int techId, @Param("state") String state, @Param("filterTerm") String filterTerm);

    @Query(value = "SELECT * FROM REQUEST WHERE TECHID = :techId AND STATE = :state AND DESCRIPTION LIKE CONCAT('%', :searchTerm, '%')", nativeQuery = true)
    List<RequestEntity> searchTechnicianRequestsByDescription(@Param("techId") int techId, @Param("state") String state, @Param("searchTerm") String searchTerm);

    @Query(value = "SELECT * FROM REQUEST WHERE USERID = :userId AND STATE= :state", nativeQuery = true)
    List<RequestEntity> getClientRequestsByState(@Param("userId")int userId,@Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE USERID = :userId AND STATE= :state ORDER BY START_DATE", nativeQuery = true)
    List<RequestEntity> sortClientRequestsByStartDate(@Param("userId") int userId,@Param("state")String state);

    @Query(value="SELECT * FROM REQUEST WHERE USERID = :userId AND STATE= :state ORDER BY END_DATE", nativeQuery = true)
    List<RequestEntity> sortClientRequestsByEndDate(@Param("userId") int userId,@Param("state")String state);

    @Query(value = "SELECT * FROM REQUEST WHERE USERID = :userId AND STATE = :state AND LOCATION LIKE CONCAT('%', :filterTerm, '%')", nativeQuery = true)
    List<RequestEntity> filterClientRequestsByLocation(@Param("userId") int userId, @Param("state") String state, @Param("filterTerm") String filterTerm);

    @Query(value = "SELECT * FROM REQUEST WHERE USERID = :userId AND STATE = :state AND DESCRIPTION LIKE CONCAT('%', :searchTerm, '%')", nativeQuery = true)
    List<RequestEntity> searchClientRequestsByDescription(@Param("userId") int userId, @Param("state") String state, @Param("searchTerm") String searchTerm);

}
