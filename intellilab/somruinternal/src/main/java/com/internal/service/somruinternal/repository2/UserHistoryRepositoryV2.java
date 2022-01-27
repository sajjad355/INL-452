package com.internal.service.somruinternal.repository2;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.UserHistoryV2;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserHistoryRepositoryV2 extends JpaRepository<UserHistoryV2, Long> {

    @Query(value =
      "Select userhistory " +
      "From UserHistoryV2 userhistory " +
      "Where Date(userhistory.activityTime) " +
      "BETWEEN CURDATE()- :days  AND  CURDATE() " +
      "ORDER BY userhistory.userHistoryId DESC")
    public List<UserHistoryV2> loadAllUserHistory(@Param("days") int days, Pageable pageable);

    @Query(value =
      "Select userhistory " +
      "From UserHistoryV2 userhistory " +
      "Where userhistory.username " +
      "LIKE %:user% and Date(userhistory.activityTime) " +
      "BETWEEN CURDATE()- :days  AND  CURDATE() " +
      "ORDER BY userhistory.userHistoryId DESC ")
    public List<UserHistoryV2> loadSpecificUserHistory(
            @Param("user") String user, @Param("days") int days, Pageable pageable);
}
