package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.internal.service.somruinternal.model2.*;

@Repository
public interface CatalogSequenceRepositoryV2 extends JpaRepository<CatalogSequenceV2, Long>{
    
    @Query(value = "SELECT c from CatalogSequenceV2 c where c.catalogNumberType = :catalogNumberType")
    public CatalogSequenceV2 findCatalogNumber(@Param("catalogNumberType") String catalogNumberType );
    
}
