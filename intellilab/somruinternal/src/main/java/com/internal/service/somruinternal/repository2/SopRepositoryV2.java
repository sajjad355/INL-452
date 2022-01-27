package com.internal.service.somruinternal.repository2;

import java.util.List;
import com.internal.service.somruinternal.model2.SopV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SopRepositoryV2 extends JpaRepository<SopV2, Long> {

    @Query(value = "Select sop from SopV2 sop where "
            + " active = TRUE and editStatus = :status ORDER By sop.modifiedOn DESC")
    public List<SopV2> findAllActiveInStatus(@Param(value = "status") String status);

    @Query(value = "Select sop from SopV2 sop where "
            + " active = TRUE ORDER By sop.modifiedOn DESC")
    public List<SopV2> findAllActiveByPage(Pageable pageable);

    @Query(value = "Select count(sop) from SopV2 sop where active = TRUE")
    public Long findAllActiveCount();

    @Query(value = "Select DISTINCT sop from SopV2 sop  Where "
            + " ( UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey% ) "
            + " AND sop.active = true ORDER By sop.sopId DESC")
    public List<SopV2> searchActiveSops(@Param(value = "searchKey") String searckKey, Pageable pageable);

    @Query(value = "Select DISTINCT sop from SopV2 sop  Where "
            + " ( UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey% ) "
            + " AND sop.active = true ORDER By sop.sopId DESC")
    public List<SopV2> searchActiveSops(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select DISTINCT sop from SopV2 sop Where "
            + "  UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey%  "
            + "  ORDER By sop.sopId DESC")
    public List<SopV2> searchAllSops(@Param(value = "searchKey") String searckKey, Pageable pageable);

    @Query(value = "Select DISTINCT sop from SopV2 sop Where "
            + "  UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey%  "
            + "  ORDER By sop.sopId DESC")
    public List<SopV2> searchAllSops(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( sop ) from SopV2 sop where "
            + " ( UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey% ) "
            + " AND sop.active = true")
    public Long searchCountActive(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( sop ) from SopV2 sop where "
            + "  UPPER(sop.sopIdentifier) LIKE %:searchKey% OR UPPER(sop.name) LIKE %:searchKey%")
    public Long searchCount(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( sop ) from SopV2 sop where "
            + "  sop.sopId <> :sopId AND "
            + "  (sop.sopIdentifier = :sopIdentifier OR sop.name = :sopName) "
            + "  AND sop.active = true")
    public Long validationCountCheck(@Param(value = "sopId") long sopId,
            @Param(value = "sopIdentifier") String sopIdentifier,
            @Param(value = "sopName") String sopName);

	public SopV2 findBySopId(Long sopId);

}
