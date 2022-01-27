package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.BlockingStep;
import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.MaterialDesignSubStep;
import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.StopStep;
import com.internal.service.somruinternal.model2.SubstrateStep;
import com.internal.service.somruinternal.model2.TestStep;
import com.internal.service.somruinternal.model2.WashStep;
import com.internal.service.somruinternal.model2.WorksheetDesign;
import com.internal.service.somruinternal.dto.WorksheetSummary;

@Repository
public interface WorksheetDesignRepository extends JpaRepository<WorksheetDesign, Long> {
	
	@Query(value = "Select NEW com.internal.service.somruinternal.dto.WorksheetSummary("
			+ "w.wsNum, w.exeNum, w.dbid,w.templateName, w.worksheetParent.dbid, w.user.userId, w.dateCreated,w.dateFinalized,w.dateSaved,w.finalized,w.scientist,w.version, w.exType, w.exObjective, w.studyNum) from WorksheetDesign w")
	public List<WorksheetSummary> findWorksheetSummaries();

	@Query(value = "Select m FROM MaterialDesignSubStep m where m in (Select materialSubstep from MaterialDesignSubStep materialSubstep JOIN materialSubstep.captureStep captureStep JOIN materialSubstep.captureStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialDesignSubStep materialSubstep JOIN materialSubstep.coatingStep coatingStep JOIN materialSubstep.coatingStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialDesignSubStep materialSubstep JOIN materialSubstep.detectionStep detectionStep JOIN materialSubstep.detectionStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialDesignSubStep materialSubstep JOIN materialSubstep.testStep testStep JOIN materialSubstep.testStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)")

	public List<MaterialDesignSubStep> getMaterialSubsteps(@Param("worksheet_id") long worksheet_id);
	
	@Query(value = "Select plateMap from WorksheetDesign worksheet "
			+ "JOIN worksheet.plateMap plateMap " + "WHERE worksheet.dbid = :worksheet_id")
	public List<PlateMap> getPlateMapFiles(@Param("worksheet_id") long worksheet_id);
	
	public WorksheetDesign findByDbid(long id);
}
