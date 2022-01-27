package com.internal.service.somruinternal.repository2;

import java.util.List;
import com.internal.service.somruinternal.model2.KitV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KitRepositoryV2 extends JpaRepository<KitV2, Long> {

    @Query(value = "Select kit from KitV2 kit where "
            + " active = TRUE and editStatus = :editStatus ORDER By kit.modifiedOn DESC")
    public List<KitV2> findAllActiveInEditStatus(@Param(value = "editStatus") String editStatus);
    
    @Query(value = "Select DISTINCT kit from KitV2 kit where "
            + " ( UPPER(kit.catalogNumber) LIKE %:searchKey% OR UPPER(kit.name) LIKE %:searchKey% ) "
            + " AND kit.active = true ORDER By kit.salesItemId DESC")
    public List<KitV2> searchActiveKits(@Param(value = "searchKey") String searckKey, Pageable pageable);
    
    @Query(value = "Select DISTINCT kit from KitV2 kit where "
            + " ( UPPER(kit.catalogNumber) LIKE %:searchKey% OR UPPER(kit.name) LIKE %:searchKey% ) "
            + " AND kit.active = true ORDER By kit.salesItemId DESC")
    public List<KitV2> searchActiveKits(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select DISTINCT kit from KitV2 kit where "
            + "  UPPER(kit.catalogNumber) LIKE %:searchKey% OR UPPER(kit.name) LIKE %:searchKey%  "
            + "  ORDER By kit.salesItemId DESC")
    public List<KitV2> searchAllKits(@Param(value = "searchKey") String searckKey, Pageable pageable);
    
    @Query(value = "Select COUNT( kit ) from KitV2 kit where "
            + "  UPPER(kit.catalogNumber) LIKE %:searchKey% OR UPPER(kit.name) LIKE %:searchKey%  "
            + " AND kit.active = true")
    public Long searchCountActive(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( kit ) from KitV2 kit where "
            + "  UPPER(kit.catalogNumber) LIKE %:searchKey% OR UPPER(kit.name) LIKE %:searchKey%  " )
    public Long searchCount(@Param(value = "searchKey") String searckKey);
    
    @Query(value = "Select COUNT( kit ) from KitV2 kit where "
            + "  kit.salesItemId <> :salesItemId AND "
            + "  (kit.catalogNumber = :catalogNumber OR kit.name = :kitName) "
            + "  AND kit.active = true")
    public Long validationCountCheck(
            @Param(value = "salesItemId") long salesItemId,
            @Param(value = "catalogNumber") String catalogNumber,
            @Param(value = "kitName") String kitName);

	public KitV2 findBySalesItemId(Long kitId);


    
}
