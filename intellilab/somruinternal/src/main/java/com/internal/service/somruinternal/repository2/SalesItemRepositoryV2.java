package com.internal.service.somruinternal.repository2;

import java.util.List;
import com.internal.service.somruinternal.model2.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesItemRepositoryV2 extends JpaRepository<SalesItemV2, Long> {
    
    @Query(value = "Select salesItem from SalesItemV2 salesItem where " +
            " (UPPER(salesItem.catalogNumber) LIKE %:searchKey% OR " +
            "  UPPER(salesItem.name) LIKE %:searchKey%) " +
            " AND salesItem.active = true " +
            " AND salesItem.editStatus = 'Approved' " +
            " Order By salesItem.catalogNumber asc")
    public List<SalesItemV2> search(@Param(value = "searchKey") String searckKey);
    
    @Query(value = "Select salesItem from SalesItemV2 salesItem where " +
            " salesItem.catalogNumber = :catalogNumber")
    public List<SalesItemV2> searchByCatalogNumber(@Param(value = "catalogNumber") String catalogNumber);
    
    
}
