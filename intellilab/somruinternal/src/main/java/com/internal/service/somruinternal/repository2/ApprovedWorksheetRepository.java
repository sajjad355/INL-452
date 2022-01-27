package com.internal.service.somruinternal.repository2;
import com.internal.service.somruinternal.model2.ApprovedWorksheet;
import com.internal.service.somruinternal.model2.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedWorksheetRepository extends JpaRepository<ApprovedWorksheet, Long>  {
}
