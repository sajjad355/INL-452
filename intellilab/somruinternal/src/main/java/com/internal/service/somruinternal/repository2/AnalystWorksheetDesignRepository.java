package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.AnalystWorksheetDesign;
import com.internal.service.somruinternal.model2.UserV2;

@Repository
public interface AnalystWorksheetDesignRepository extends JpaRepository<AnalystWorksheetDesign, Long> {
	
	public List<AnalystWorksheetDesign> findByAnalyst(UserV2 analyst); 

}
