package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.BlockingStepInstance;
import com.internal.service.somruinternal.model2.CaptureStepInstance;
import com.internal.service.somruinternal.model2.CoatingStepInstance;
import com.internal.service.somruinternal.model2.DetectionStepInstance;
import com.internal.service.somruinternal.model2.MaterialDesignSubStep;
import com.internal.service.somruinternal.model2.MaterialInstanceSubStep;
import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.Result;
import com.internal.service.somruinternal.model2.StopStepInstance;
import com.internal.service.somruinternal.model2.SubstrateStepInstance;
import com.internal.service.somruinternal.model2.TestStepInstance;
import com.internal.service.somruinternal.model2.WashStepInstance;
import com.internal.service.somruinternal.model2.WorksheetInstance;

@Repository
public interface WorksheetInstanceRepository extends JpaRepository<WorksheetInstance, Long> {

	@Query(value = "SELECT w from WorksheetInstance w where w.status = 'incomplete'")
	public List<WorksheetInstance> findIncompleteWorksheetInstances();

	@Query(value = "SELECT w from WorksheetInstance w where w.status = 'complete'")
	public List<WorksheetInstance> findCompleteWorksheetInstances();

	@Query(value = "SELECT w from WorksheetInstance w where w.status = 'approved'")
	public List<WorksheetInstance> findApprovedWorksheetInstances();

	@Query(value = "SELECT w from WorksheetInstance w where w.status = 'rejected'")
	public List<WorksheetInstance> findRejectedWorksheetInstances();

//	@Query(value = "Select stopstep from WorksheetInstance worksheet " + "JOIN worksheet.stopSteps stopstep "
//			+ "WHERE worksheet.dbid = :worksheet_id " + "ORDER By stopstep.position ASC")
//	public List<StopStepInstance> getStopSteps(@Param("worksheet_id") long worksheet_id);

//	@Query(value = "Select washstep from WorksheetInstance worksheet " + "JOIN worksheet.washSteps washstep "
//			+ "WHERE worksheet.dbid = :worksheet_id " + "ORDER By washstep.position ASC")
//	public List<WashStepInstance> getWashSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select coatingStep from WorksheetInstance worksheet " + "JOIN worksheet.coatingSteps coatingStep "
//			+ "WHERE worksheet.dbid = :worksheet_id " + "ORDER By coatingStep.position ASC")
//	public List<CoatingStepInstance> getCoatingSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select blockingStep from WorksheetInstance worksheet "
//			+ "JOIN worksheet.blockingSteps blockingStep " + "WHERE worksheet.dbid = :worksheet_id "
//			+ "ORDER By blockingStep.position ASC")
//	public List<BlockingStepInstance> getBlockingSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select captureStep from WorksheetInstance worksheet " + "JOIN worksheet.captureSteps captureStep "
//			+ "WHERE worksheet.dbid = :worksheet_id " + "ORDER By captureStep.position ASC")
//	public List<CaptureStepInstance> getCaptureSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select testStep from WorksheetInstance worksheet " + "JOIN worksheet.testSteps testStep "
//			+ "WHERE worksheet.dbid = :worksheet_id " + "ORDER By testStep.position ASC")
//	public List<TestStepInstance> getTestSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select detectionStep from WorksheetInstance worksheet "
//			+ "JOIN worksheet.detectionSteps detectionStep " + "WHERE worksheet.dbid = :worksheet_id "
//			+ "ORDER By detectionStep.position ASC")
//	public List<DetectionStepInstance> getDetectionSteps(@Param("worksheet_id") long worksheet_id);
//
//	@Query(value = "Select substrateStep from WorksheetInstance worksheet "
//			+ "JOIN worksheet.substrateSteps substrateStep " + "WHERE worksheet.dbid = :worksheet_id "
//			+ "ORDER By substrateStep.position ASC")
//	public List<SubstrateStepInstance> getSubstrateSteps(@Param("worksheet_id") long worksheet_id);
	
	@Query(value = "Select m FROM MaterialInstanceSubStep m where m in (Select materialSubstep from MaterialInstanceSubStep materialSubstep JOIN materialSubstep.captureStep captureStep JOIN materialSubstep.captureStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialInstanceSubStep materialSubstep JOIN materialSubstep.coatingStep coatingStep JOIN materialSubstep.coatingStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialInstanceSubStep materialSubstep JOIN materialSubstep.detectionStep detectionStep JOIN materialSubstep.detectionStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)"

			+ "or m in (Select materialSubstep from MaterialInstanceSubStep materialSubstep JOIN materialSubstep.testStep testStep JOIN materialSubstep.testStep.worksheet worksheet WHERE worksheet.dbid = :worksheet_id)")

	public List<MaterialInstanceSubStep> getMaterialSubsteps(@Param("worksheet_id") long worksheet_id);
	
	@Query(value = "Select result from WorksheetInstance worksheet "
			+ "JOIN worksheet.results result " + "WHERE worksheet.dbid = :worksheet_id")
	public List<Result> getResultFiles(@Param("worksheet_id") long worksheet_id);

	public WorksheetInstance findByDbid(Long inputInstanceId);

	@Query("SELECT MAX(exVersion) FROM WorksheetInstance WHERE worksheetDesign.dbid = :templateId")
	public Integer findMaxExVersionByDesign(@Param("templateId") long templateId);

}
