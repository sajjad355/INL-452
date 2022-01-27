package com.internal.service.somruinternal.repository2;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.service.somruinternal.model2.WorksheetDesign;
import com.internal.service.somruinternal.model2.WorksheetInstance;
import com.internal.service.somruinternal.model2.WorksheetStep;

public interface WorksheetStepRepository extends JpaRepository<WorksheetStep, Long>{

	List<WorksheetStep> findByWorksheetAndDbidNotIn(WorksheetInstance wsInstance, List<Long> inputStepIdList);

	Set<WorksheetStep> findByTemplate(WorksheetDesign worksheet);

	List<WorksheetStep> findByWorksheet(WorksheetInstance wsInstance);
}
