
package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.internal.service.somruinternal.model2.*;

@Repository
public interface SalesNumberSequenceRepository extends JpaRepository<SalesNumberSequenceV2, Long>{
    
    @Query(value = "SELECT s from SalesNumberSequenceV2 s where s.salesNumberSequenceType = :sequenceType")
    public SalesNumberSequenceV2 findNextSequenceNumber(@Param("sequenceType") String sequenceType );
    
}
