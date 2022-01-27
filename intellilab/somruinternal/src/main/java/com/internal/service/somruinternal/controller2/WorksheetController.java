package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.repository2.WorksheetRepository;
import com.internal.service.somruinternal.repository2.ApprovedWorksheetRepository;
import com.internal.service.somruinternal.model2.Worksheet;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.ApprovedWorksheet;
import com.internal.service.somruinternal.model2.ClientCompanyV2;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/worksheet")
public class WorksheetController extends ParentControl {

	@Autowired
	private WorksheetRepository worksheetRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(WorksheetController.class);

//  @Autowired
//  private UserHistoryRepository userHistoryRepo;

	@Autowired
	private ApprovedWorksheetRepository approvedWorksheetRepository;

	@PostMapping("/addapproved")
	@Transactional(rollbackFor = Exception.class)
	public ApprovedWorksheet addApprovedWorksheet(@Valid @RequestBody ApprovedWorksheet approvedWorksheet) {
		Worksheet worksheet = worksheetRepository.findByWorksheetID(approvedWorksheet.getWorksheetID());
		approvedWorksheet.setDate(worksheet.getDate());
		ApprovedWorksheet savedWorksheet = approvedWorksheetRepository.save(approvedWorksheet);
		super.saveUserHistory(savedWorksheet.getScientist(), "WorkSheet", "addApprovedWorksheet",
				savedWorksheet.toString());
		return savedWorksheet;
	}

	@GetMapping("/allapproved")
	public List<ApprovedWorksheet> getAllApprovedWorksheet() {
		return approvedWorksheetRepository.findAll();
	}

	@GetMapping("/alldeclined")
	public List<Worksheet> getAllDeclinedWorksheet() {
		return worksheetRepository.getAllWorksheetByDeclinedStatus();
	}

	private Worksheet loadWorksheet(Long workSheetId) {
		Worksheet Worksheet = worksheetRepository.findByWorksheetID(workSheetId);
		if (Worksheet == null) {
			LOGGER.info("worksheet not found");
			throw new EntityNotFoundException(ClientCompanyV2.class, "id", workSheetId.toString());
		}
		return Worksheet;
	}

	@PostMapping("/add")
	@Transactional(rollbackFor = Exception.class)
	public Worksheet addWorksheet(@Valid @RequestBody Worksheet worksheet) {
		String action;
		String auditTrailRecord;
		if (worksheet.getWorksheetID() > 0) {
			Worksheet previousWorksheetDetails = this.loadWorksheet(worksheet.getWorksheetID());
			action = "update worksheet";
			auditTrailRecord = getDiffs(worksheet, previousWorksheetDetails);
		} else {
			action = "insert worksheet";
			auditTrailRecord = worksheet.toString();
		}
		Worksheet savedWorkSheet = worksheetRepository.save(worksheet);
		super.saveUserHistory(savedWorkSheet.getScientist(), "WorkSheet", "addWorksheet", savedWorkSheet.toString());
		return savedWorkSheet;
	}

	private String getDiffs(Worksheet lhs, Worksheet rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Worksheet objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("Worksheet GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@GetMapping("/all")
	public List<Worksheet> getAllWorksheet() {
		return worksheetRepository.findAll();
	}

	@GetMapping("/get/{worksheetID}")
	public ResponseEntity<Worksheet> getWorksheetById(@PathVariable(value = "worksheetID") Long worksheetID) {
		Worksheet worksheet = worksheetRepository.findByWorksheetID(worksheetID);
		if (worksheet == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(worksheet);
	}

	@GetMapping("/complete")
	public List<Worksheet> getAllCompleteWorksheet() {
		return worksheetRepository.getAllWorksheetByCompleteStatus();
	}

	@GetMapping("/inprogress")
	public List<Worksheet> getAllWorksheetInProgress() {
		return worksheetRepository.getAllWorksheetByInProgressStatus();
	}

	@GetMapping("/qc")
	public List<Worksheet> getAllWorksheetInQualityControl() {
		return worksheetRepository.getAllWorksheetByPassedReviewStatus();
	}

	@PutMapping("/updatereview/{worksheetID}")
	public ResponseEntity<Worksheet> updateWorksheetReviewStatus(@PathVariable(value = "worksheetID") Long worksheetID,
			@Valid @RequestBody Worksheet worksheetDetail) {
		Worksheet worksheet = worksheetRepository.findByWorksheetID(worksheetID);
		if (worksheet == null) {
			return ResponseEntity.notFound().build();
		}

		worksheet.setReviewComment(worksheetDetail.getReviewComment());
		worksheet.setStatus(worksheetDetail.getStatus());

		Worksheet updateWorksheet = worksheetRepository.save(worksheet);
		return ResponseEntity.ok(updateWorksheet);

	}

	@PutMapping("/updateqc/{worksheetID}")
	public ResponseEntity<Worksheet> updateWorksheetQCStatus(@PathVariable(value = "worksheetID") Long worksheetID,
			@Valid @RequestBody Worksheet worksheetDetail) {
		Worksheet worksheet = worksheetRepository.findByWorksheetID(worksheetID);
		if (worksheet == null) {
			return ResponseEntity.notFound().build();
		}

		worksheet.setQcComment(worksheetDetail.getQcComment());
		worksheet.setReviewComment(worksheetDetail.getQcComment());
		worksheet.setStatus(worksheetDetail.getStatus());

		Worksheet updateWorksheet = worksheetRepository.save(worksheet);
		return ResponseEntity.ok(updateWorksheet);

	}

	@PutMapping("/update/{worksheetID}")
	public ResponseEntity<Worksheet> updateWorksheet(@PathVariable(value = "worksheetID") Long worksheetID,
			@Valid @RequestBody Worksheet worksheetDetail) {
		Worksheet worksheet = worksheetRepository.findByWorksheetID(worksheetID);
		if (worksheet == null) {
			return ResponseEntity.notFound().build();
		}

		// =================================Section
		// One=======================================

		worksheet.setAssayNumber(worksheetDetail.getAssayNumber());
		worksheet.setScientist(worksheetDetail.getScientist());
		// worksheet.setDate(worksheetDetail.getDate());
		worksheet.setStatus(worksheetDetail.getStatus());

		// =====================================END=======================================

		worksheet.setStep2_CoatingBuffer(worksheetDetail.getStep2_CoatingBuffer());
		worksheet.setStep2_Supplier_1(worksheetDetail.getStep2_Supplier_1());
		worksheet.setStep2_CoatingBufferLotNumber(worksheetDetail.getStep2_CoatingBufferLotNumber());
		worksheet.setStep2_CoatingMaterial1(worksheetDetail.getStep2_CoatingMaterial1());
		worksheet.setStep2_Supplier_2(worksheetDetail.getStep2_Supplier_2());
		worksheet.setStep2_Concentration_1(worksheetDetail.getStep2_Concentration_1());
		worksheet.setStep2_CoatingMaterial1LotNumber(worksheetDetail.getStep2_CoatingMaterial1LotNumber());
		worksheet.setStep2_ulCoatingMaterial(worksheetDetail.getStep2_ulCoatingMaterial());
		worksheet.setStep2_ulCoatingBuffer(worksheetDetail.getStep2_ulCoatingBuffer());
		worksheet.setStep2_ulCoatingMaterial_ulCoatingBuffer(
				worksheetDetail.getStep2_ulCoatingMaterial_ulCoatingBuffer());
		worksheet.setStep2_CheckedBox_1(worksheetDetail.isStep2_CheckedBox_1());
		worksheet.setStep2_CheckedBox_2(worksheetDetail.isStep2_CheckedBox_2());
		worksheet.setStep2_Time(worksheetDetail.getStep2_Time());
		worksheet.setStep2_Item_1(worksheetDetail.getStep2_Item_1());
		worksheet.setStep2_LotNum(worksheetDetail.getStep2_LotNum());
		worksheet.setStep2_ConcentrationAmount(worksheetDetail.getStep2_ConcentrationAmount());
		worksheet.setStep2_Item_2(worksheetDetail.getStep2_Item_2());
		worksheet.setStep2_LotNum_1(worksheetDetail.getStep2_LotNum_1());
		worksheet.setStep2_Results_0(worksheetDetail.getStep2_Results_0());
		worksheet.setStep2_ulCoatingMaterial_0(worksheetDetail.getStep2_ulCoatingMaterial_0());
		worksheet.setStep2_CoatingMaterial_0(worksheetDetail.getStep2_CoatingMaterial_0());
		worksheet.setStep2_ulCoatingBuffer_1(worksheetDetail.getStep2_ulCoatingBuffer_1());
		worksheet.setStep2_CoatingMaterial_2(worksheetDetail.getStep2_CoatingMaterial_2());
		worksheet.setStep2_CoatingMaterial_3(worksheetDetail.getStep2_CoatingMaterial_3());
		worksheet.setStep2_CoatingMaterial_4(worksheetDetail.getStep2_CoatingMaterial_4());
		worksheet.setStep2_ulCoatingMaterial_1(worksheetDetail.getStep2_ulCoatingMaterial_1());
		worksheet.setStep2_ulCoatingMaterial_2(worksheetDetail.getStep2_ulCoatingMaterial_2());
		worksheet.setStep2_CoatingMaterial_5(worksheetDetail.getStep2_CoatingMaterial_5());
		worksheet.setStep2_CoatingMaterial_6(worksheetDetail.getStep2_CoatingMaterial_6());
		worksheet.setStep2_ulCoatingBuffer_2(worksheetDetail.getStep2_ulCoatingBuffer_2());
		worksheet.setStep2_ConcentrationAmount_1(worksheetDetail.getStep2_ConcentrationAmount_1());
		worksheet.setStep2_ConcentrationLotNum(worksheetDetail.getStep2_ConcentrationLotNum());
		worksheet.setStep2_ulCoatingMaterial_3(worksheetDetail.getStep2_ulCoatingMaterial_3());
		worksheet.setStep2_CoatingMaterial_7(worksheetDetail.getStep2_CoatingMaterial_7());
		worksheet.setStep2_Results(worksheetDetail.getStep2_Results());
		worksheet.setStep2_Item_3(worksheetDetail.getStep2_Item_3());
		worksheet.setStep2_Supplier_3(worksheetDetail.getStep2_Supplier_3());
		worksheet.setStep2_ulCoatingMaterial_4(worksheetDetail.getStep2_ulCoatingMaterial_4());
		worksheet.setStep2_CoatingMaterial_8(worksheetDetail.getStep2_CoatingMaterial_8());
		worksheet.setStep2_ulCoatingBuffer_3(worksheetDetail.getStep2_ulCoatingBuffer_3());
		worksheet.setStep2_Results_2(worksheetDetail.getStep2_Results_2());
		worksheet.setStep2_Item_9(worksheetDetail.getStep2_Item_9());
		worksheet.setStep2_Supplier_9(worksheetDetail.getStep2_Supplier_9());
		worksheet.setStep2_Concentration_2(worksheetDetail.getStep2_Concentration_2());
		worksheet.setStep2_LotNum_2(worksheetDetail.getStep2_LotNum_2());
		worksheet.setStep2_CoatingMaterial_9(worksheetDetail.getStep2_CoatingMaterial_9());
		worksheet.setStep2_ulCoatingBuffer_4(worksheetDetail.getStep2_ulCoatingBuffer_4());
		worksheet.setStep2_Results_3(worksheetDetail.getStep2_Results_3());
		worksheet.setStep2_CoatingMaterial_10(worksheetDetail.getStep2_CoatingMaterial_10());
		worksheet.setStep2_CoatingMaterial_11(worksheetDetail.getStep2_CoatingMaterial_11());
		worksheet.setStep2_ulCoatingBuffer_5(worksheetDetail.getStep2_ulCoatingBuffer_5());
		worksheet.setStep2_Results_4(worksheetDetail.getStep2_Results_4());
		worksheet.setStep2_Item_4(worksheetDetail.getStep2_Item_4());
		worksheet.setStep2_Supplier_4(worksheetDetail.getStep2_Supplier_4());
		worksheet.setStep2_Concentration_3(worksheetDetail.getStep2_Concentration_3());
		worksheet.setStep2_Item_5(worksheetDetail.getStep2_Item_5());
		worksheet.setStep2_Supplier_5(worksheetDetail.getStep2_Supplier_5());
		worksheet.setStep2_Concentration_7(worksheetDetail.getStep2_Concentration_7());
		worksheet.setStep2_LotNum_3(worksheetDetail.getStep2_LotNum_3());
		worksheet.setStep2_CoatingMaterial_25(worksheetDetail.getStep2_CoatingMaterial_25());
		worksheet.setStep2_ulCoatingBuffer_14(worksheetDetail.getStep2_ulCoatingBuffer_14());
		worksheet.setStep2_Results_13(worksheetDetail.getStep2_Results_13());
		worksheet.setStep2_CoatingMaterial_26(worksheetDetail.getStep2_CoatingMaterial_26());
		worksheet.setStep2_CoatingMaterial_27(worksheetDetail.getStep2_CoatingMaterial_27());
		worksheet.setStep2_CoatingBuffer_15(worksheetDetail.getStep2_CoatingBuffer_15());
		worksheet.setStep2_Results_14(worksheetDetail.getStep2_Results_14());
		worksheet.setStep2_CoatingMaterial_12(worksheetDetail.getStep2_CoatingMaterial_12());
		worksheet.setStep2_ulCoatingBuffer_6(worksheetDetail.getStep2_ulCoatingBuffer_6());
		worksheet.setStep2_Results_5(worksheetDetail.getStep2_Results_5());
		worksheet.setStep2_CoatingMaterial_13(worksheetDetail.getStep2_CoatingMaterial_13());
		worksheet.setStep2_CoatingMaterial_14(worksheetDetail.getStep2_CoatingMaterial_14());
		worksheet.setStep2_CoatingBuffer_7(worksheetDetail.getStep2_CoatingBuffer_7());
		worksheet.setStep2_Results_6(worksheetDetail.getStep2_Results_6());
		worksheet.setStep2_Item_6(worksheetDetail.getStep2_Item_6());
		worksheet.setStep2_Supplier_6(worksheetDetail.getStep2_Supplier_6());
		worksheet.setStep2_Concentration_4(worksheetDetail.getStep2_Concentration_4());
		worksheet.setStep2_LotNum_4(worksheetDetail.getStep2_LotNum_4());
		worksheet.setStep2_CoatingMaterial_15(worksheetDetail.getStep2_CoatingMaterial_15());
		worksheet.setStep2_ulCoatingBuffer_7(worksheetDetail.getStep2_ulCoatingBuffer_7());
		worksheet.setStep2_Results_7(worksheetDetail.getStep2_Results_7());
		worksheet.setStep2_CoatingMaterial_16(worksheetDetail.getStep2_CoatingMaterial_16());
		worksheet.setStep2_CoatingMaterial_17(worksheetDetail.getStep2_CoatingMaterial_17());
		worksheet.setStep2_CoatingBuffer_8(worksheetDetail.getStep2_CoatingBuffer_8());
		worksheet.setStep2_Results_8(worksheetDetail.getStep2_Results_8());
		worksheet.setStep2_Item_7(worksheetDetail.getStep2_Item_7());
		worksheet.setStep2_Supplier_7(worksheetDetail.getStep2_Supplier_7());
		worksheet.setStep2_Concentration_5(worksheetDetail.getStep2_Concentration_5());
		worksheet.setStep2_LotNum_7(worksheetDetail.getStep2_LotNum_7());
		worksheet.setStep4_LotNum_5(worksheetDetail.getStep4_LotNum_5());
		worksheet.setStep2_CoatingMaterial_18(worksheetDetail.getStep2_CoatingMaterial_18());
		worksheet.setStep2_CoatingBuffer_10(worksheetDetail.getStep2_CoatingBuffer_10());
		worksheet.setStep2_Results_9(worksheetDetail.getStep2_Results_9());
		worksheet.setStep2_CoatingMaterial_19(worksheetDetail.getStep2_CoatingMaterial_19());
		worksheet.setStep2_CoatingMaterial_20(worksheetDetail.getStep2_CoatingMaterial_20());
		worksheet.setStep2_CoatingBuffer_11(worksheetDetail.getStep2_CoatingBuffer_11());
		worksheet.setStep2_Results_10(worksheetDetail.getStep2_Results_10());
		worksheet.setStep2_Item_8(worksheetDetail.getStep2_Item_8());
		worksheet.setStep2_Supplier_8(worksheetDetail.getStep2_Supplier_8());
		worksheet.setStep2_LotNum_6(worksheetDetail.getStep2_LotNum_6());
		worksheet.setStep2_CoatingMaterial_21(worksheetDetail.getStep2_CoatingMaterial_21());
		worksheet.setStep2_Results_11(worksheetDetail.getStep2_Results_11());
		worksheet.setStep2_CoatingMaterial_1(worksheetDetail.getStep2_CoatingMaterial_1());
		worksheet.setStep2_ulCoatingBuffer_0(worksheetDetail.getStep2_ulCoatingBuffer_0());
		worksheet.setStep2_addingAmount(worksheetDetail.getStep2_addingAmount());
		worksheet.setStep2_Results_1(worksheetDetail.getStep2_Results_1());
		worksheet.setStep2_addingAmount_1(worksheetDetail.getStep2_addingAmount_1());
		worksheet.setStep2_CoatingBuffer_2(worksheetDetail.getStep2_CoatingBuffer_2());
		worksheet.setStep2_CoatingBuffer_3(worksheetDetail.getStep2_CoatingBuffer_3());
		worksheet.setStep2_addingAmount_2(worksheetDetail.getStep2_addingAmount_2());
		worksheet.setStep2_ulCoatingMaterial_8(worksheetDetail.getStep2_ulCoatingMaterial_8());
		worksheet.setStep2_addingAmount_8(worksheetDetail.getStep2_addingAmount_8());
		worksheet.setStep2_ulCoatingBuffer_10(worksheetDetail.getStep2_ulCoatingBuffer_10());
		worksheet.setStep2_LotNum_5(worksheetDetail.getStep2_LotNum_5());
		worksheet.setStep2_ulCoatingMaterial_5(worksheetDetail.getStep2_ulCoatingMaterial_5());
		worksheet.setStep2_ulCoatingMaterial_6(worksheetDetail.getStep2_ulCoatingMaterial_6());
		worksheet.setStep2_addingAmount_3(worksheetDetail.getStep2_addingAmount_3());
		worksheet.setStep2_ulCoatingMaterial_7(worksheetDetail.getStep2_ulCoatingMaterial_7());
		worksheet.setStep2_addingAmount_4(worksheetDetail.getStep2_addingAmount_4());
		worksheet.setStep2_ulCoatingBuffer_8(worksheetDetail.getStep2_ulCoatingBuffer_8());

		// =================================== END =====================================

		// ============================= Section Three =================================

		worksheet.setStep3_Item_1(worksheetDetail.getStep3_Item_1());
		worksheet.setStep3_Supplier_1(worksheetDetail.getStep3_Supplier_1());
		worksheet.setStep3_LotNum(worksheetDetail.getStep3_LotNum());
		worksheet.setStep3_CheckedBox_1(worksheetDetail.isStep3_CheckedBox_1());
		worksheet.setStep3_CheckedBox_2(worksheetDetail.isStep3_CheckedBox_2());
		worksheet.setStep3_Time(worksheetDetail.getStep3_Time());

		// =================================== END =====================================

		// ============================= Section Four ================================

		worksheet.setStep4_Item_1(worksheetDetail.getStep4_Item_1());
		worksheet.setStep4_Supplier_1(worksheetDetail.getStep4_Supplier_1());
		worksheet.setStep4_LotNum(worksheetDetail.getStep4_LotNum());
		worksheet.setStep4_CheckedBox_1(worksheetDetail.isStep4_CheckedBox_1());
		worksheet.setStep4_CheckedBox_2(worksheetDetail.isStep4_CheckedBox_2());
		worksheet.setStep4_Time(worksheetDetail.getStep4_Time());

		// =================================== END =====================================

		// ============================= Section Five ==================================

		worksheet.setStep5_Item_1(worksheetDetail.getStep5_Item_1());
		worksheet.setStep5_Supplier_1(worksheetDetail.getStep5_Supplier_1());
		worksheet.setStep5_LotNum(worksheetDetail.getStep5_LotNum());
		worksheet.setStep5_CloneId_1(worksheetDetail.getStep5_CloneId_1());
		worksheet.setStep5_ulClone_1(worksheetDetail.getStep5_ulClone_1());
		worksheet.setStep5_ulClone_2(worksheetDetail.getStep5_ulClone_2());
		worksheet.setStep5_ulDiluent(worksheetDetail.getStep5_ulDiluent());
		worksheet.setStep5_CheckedBox_1(worksheetDetail.isStep5_CheckedBox_1());
		worksheet.setStep5_CheckedBox_2(worksheetDetail.isStep5_CheckedBox_2());
		worksheet.setStep5_Time(worksheetDetail.getStep5_Time());

		// =================================== END ====================================

		// ============================= Section Six ==================================

		worksheet.setStep6_Item_1(worksheetDetail.getStep6_Item_1());
		worksheet.setStep6_Supplier_1(worksheetDetail.getStep6_Supplier_1());
		worksheet.setStep6_LotNum(worksheetDetail.getStep6_LotNum());
		worksheet.setStep6_Item_2(worksheetDetail.getStep6_Item_2());
		worksheet.setStep6_Supplier_2(worksheetDetail.getStep6_Supplier_2());
		worksheet.setStep6_Concentration_1(worksheetDetail.getStep6_Concentration_1());
		worksheet.setStep6_LotNum_1(worksheetDetail.getStep6_LotNum_1());
		worksheet.setStep6_ulAvastin(worksheetDetail.getStep6_ulAvastin());
		worksheet.setStep6_ulSuperblock(worksheetDetail.getStep6_ulSuperblock());
		worksheet.setStep6_Results(worksheetDetail.getStep6_Results());
		worksheet.setStep6_ulAvastin1(worksheetDetail.getStep6_ulAvastin1());
		worksheet.setStep6_addingAmount(worksheetDetail.getStep6_addingAmount());
		worksheet.setStep6_ulSuperblock2(worksheetDetail.getStep6_ulSuperblock2());
		worksheet.setStep6_Results_2(worksheetDetail.getStep6_Results_2());
		worksheet.setStep6_Item_3(worksheetDetail.getStep6_Item_3());
		worksheet.setStep6_Concentration_2(worksheetDetail.getStep6_Concentration_2());
		worksheet.setStep6_Concentration_LotNum(worksheetDetail.getStep6_Concentration_LotNum());
		worksheet.setStep6_ulBiosimilar(worksheetDetail.getStep6_ulBiosimilar());
		worksheet.setStep6_ulSuperblock3(worksheetDetail.getStep6_ulSuperblock3());
		worksheet.setStep6_Results_3(worksheetDetail.getStep6_Results_3());
		worksheet.setStep6_ulBiosimilar2(worksheetDetail.getStep6_ulBiosimilar2());
		worksheet.setStep6_addingAmount_2(worksheetDetail.getStep6_addingAmount_2());
		worksheet.setStep6_Superblock4(worksheetDetail.getStep6_Superblock4());
		worksheet.setStep6_Results_4(worksheetDetail.getStep6_Results_4());
		worksheet.setStep6_Item_4(worksheetDetail.getStep6_Item_4());
		worksheet.setStep6_Supplier_4(worksheetDetail.getStep6_Supplier_4());
		worksheet.setStep6_Concentration_3(worksheetDetail.getStep6_Concentration_3());
		worksheet.setStep6_LotNum_2(worksheetDetail.getStep6_LotNum_2());
		worksheet.setStep6_ulStandard(worksheetDetail.getStep6_ulStandard());
		worksheet.setStep6_ulSuperblock5(worksheetDetail.getStep6_ulSuperblock5());
		worksheet.setStep6_Results_5(worksheetDetail.getStep6_Results_5());
		worksheet.setStep6_ulStandard2(worksheetDetail.getStep6_ulStandard2());
		worksheet.setStep6_addingAmount_3(worksheetDetail.getStep6_addingAmount_3());
		worksheet.setStep6_ulSuperblock6(worksheetDetail.getStep6_ulSuperblock6());
		worksheet.setStep6_Results_6(worksheetDetail.getStep6_Results_6());
		worksheet.setStep6_Supplier_3(worksheetDetail.getStep6_Supplier_3());
		worksheet.setStep6_CheckedBox_1(worksheetDetail.isStep6_CheckedBox_1());
		worksheet.setStep6_CheckedBox_2(worksheetDetail.isStep6_CheckedBox_2());
		worksheet.setStep6_Time(worksheetDetail.getStep6_Time());
		worksheet.setStep6_Concentration(worksheetDetail.getStep6_Concentration());
		worksheet.setStep6_ulAvastin_1(worksheetDetail.getStep6_ulAvastin_1());
		worksheet.setStep6_ulSuperblock_1(worksheetDetail.getStep6_ulSuperblock_1());
		worksheet.setStep6_Results_1(worksheetDetail.getStep6_Results_1());
		worksheet.setStep6_LotNum_3(worksheetDetail.getStep6_LotNum_3());
		worksheet.setStep6_ulNistmAb(worksheetDetail.getStep6_ulNistmAb());
		worksheet.setStep6_ulSuperblock_2(worksheetDetail.getStep6_ulSuperblock_2());
		worksheet.setStep6_ulNistmAb_2(worksheetDetail.getStep6_ulNistmAb_2());
		worksheet.setStep6_addingAmount_1(worksheetDetail.getStep6_addingAmount_1());
		worksheet.setStep6_ulSuperblock_3(worksheetDetail.getStep6_ulSuperblock_3());
		worksheet.setStep6_LotNum_4(worksheetDetail.getStep6_LotNum_4());
		worksheet.setStep6_ulSuperblock_4(worksheetDetail.getStep6_ulSuperblock_4());
		worksheet.setStep6_ulBeavatas(worksheetDetail.getStep6_ulBeavatas());
		worksheet.setStep6_ulBeavatas_1(worksheetDetail.getStep6_ulBeavatas_1());
		worksheet.setStep6_ulSuperblock_5(worksheetDetail.getStep6_ulSuperblock_5());

		// =================================== END =====================================

		// ============================= Section Seven =================================

		worksheet.setStep7_Item_1(worksheetDetail.getStep7_Item_1());
		worksheet.setStep7_Supplier_1(worksheetDetail.getStep7_Supplier_1());
		worksheet.setStep7_CoatingMaterial0(worksheetDetail.getStep7_CoatingMaterial0());
		worksheet.setStep7_CheckedBox_1(worksheetDetail.isStep7_CheckedBox_1());
		worksheet.setStep7_LotNum(worksheetDetail.getStep7_LotNum());
		worksheet.setStep7_LotNum_1(worksheetDetail.getStep7_LotNum_1());
		worksheet.setStep7_Item_2(worksheetDetail.getStep7_Item_2());
		worksheet.setStep7_Supplier_2(worksheetDetail.getStep7_Supplier_2());
		worksheet.setStep7_LotNum_2(worksheetDetail.getStep7_LotNum_2());
		worksheet.setStep7_ulConjugate(worksheetDetail.getStep7_ulConjugate());
		worksheet.setStep7_ulDiluent(worksheetDetail.getStep7_ulDiluent());
		worksheet.setStep7_ul(worksheetDetail.getStep7_ul());
		worksheet.setStep7_Results(worksheetDetail.getStep7_Results());
		worksheet.setStep7_addingAmount(worksheetDetail.getStep7_addingAmount());
		worksheet.setStep7_ulDiluent_1(worksheetDetail.getStep7_ulDiluent_1());
		worksheet.setStep7_Results_1(worksheetDetail.getStep7_Results_1());
		worksheet.setStep7_CheckedBox_2(worksheetDetail.isStep7_CheckedBox_2());
		worksheet.setStep7_Concentration_1(worksheetDetail.getStep7_Concentration_1());
		worksheet.setStep7_Time(worksheetDetail.getStep7_Time());

		// =================================== END =====================================

		// ============================= Section Eight ===============================

		worksheet.setStep8_Item_1(worksheetDetail.getStep8_Item_1());
		worksheet.setStep8_Supplier_1(worksheetDetail.getStep8_Supplier_1());
		worksheet.setStep8_LotNum(worksheetDetail.getStep8_LotNum());
		worksheet.setStep8_ulConjugate(worksheetDetail.getStep8_ulConjugate());
		worksheet.setStep8_Item_2(worksheetDetail.getStep8_Item_2());
		worksheet.setStep8_Supplier_2(worksheetDetail.getStep8_Supplier_2());
		worksheet.setStep8_LotNum_1(worksheetDetail.getStep8_LotNum_1());
		worksheet.setStep8_Results(worksheetDetail.getStep8_Results());
		worksheet.setStep8_ulAmount(worksheetDetail.getStep8_ulAmount());
		worksheet.setStep8_ulDiluent(worksheetDetail.getStep8_ulDiluent());
		worksheet.setStep8_ulDiluent_1(worksheetDetail.getStep8_ulDiluent_1());
		worksheet.setStep8_Results_1(worksheetDetail.getStep8_Results_1());
		worksheet.setStep8_addingAmount(worksheetDetail.getStep8_addingAmount());
		worksheet.setStep8_CheckedBox_1(worksheetDetail.isStep8_CheckedBox_1());
		worksheet.setStep8_CheckedBox_2(worksheetDetail.isStep8_CheckedBox_2());
		worksheet.setStep8_Time(worksheetDetail.getStep8_Time());
		worksheet.setStep8_Concentration_1(worksheetDetail.getStep8_Concentration_1());
		worksheet.setStep8_ulDectection(worksheetDetail.getStep8_ulDectection());

		// =================================== END =====================================

		// ============================= Section Nine ================================

		worksheet.setStep9_Item_1(worksheetDetail.getStep9_Item_1());
		worksheet.setStep9_Supplier_1(worksheetDetail.getStep9_Supplier_1());
		worksheet.setStep9_LotNum(worksheetDetail.getStep9_LotNum());
		worksheet.setStep9_CheckedBox_1(worksheetDetail.isStep9_CheckedBox_1());
		worksheet.setStep9_mlAmount(worksheetDetail.getStep9_mlAmount());
		worksheet.setStep9_mlAmount_1(worksheetDetail.getStep9_mlAmount_1());
		worksheet.setStep9_CheckedBox_2(worksheetDetail.isStep9_CheckedBox_2());
		worksheet.setStep9_CheckedBox_3(worksheetDetail.isStep9_CheckedBox_3());
		worksheet.setStep9_CheckedBox_4(worksheetDetail.isStep9_CheckedBox_4());

		// =================================== END ====================================

		// ============================= Section Ten ==================================

		worksheet.setStep10_Item_1(worksheetDetail.getStep10_Item_1());
		worksheet.setStep10_Item_2(worksheetDetail.getStep10_Item_2());
		worksheet.setStep10_Supplier_1(worksheetDetail.getStep10_Supplier_1());
		worksheet.setStep10_Supplier_2(worksheetDetail.getStep10_Supplier_2());
		worksheet.setStep10_LotNum(worksheetDetail.getStep10_LotNum());
		worksheet.setStep10_LotNum_1(worksheetDetail.getStep10_LotNum_1());
		worksheet.setStep10_ulDetection(worksheetDetail.getStep10_ulDetection());
		worksheet.setStep10_ulDiluent(worksheetDetail.getStep10_ulDiluent());
		worksheet.setStep10_Results(worksheetDetail.getStep10_Results());
		worksheet.setStep10_ulDectection1(worksheetDetail.getStep10_ulDectection1());
		worksheet.setStep10_addingAmount(worksheetDetail.getStep10_addingAmount());
		worksheet.setStep10_ulDiluent1(worksheetDetail.getStep10_ulDiluent1());
		worksheet.setStep10_Results_1(worksheetDetail.getStep10_Results_1());
		worksheet.setStep10_CheckedBox_1(worksheetDetail.isStep10_CheckedBox_1());
		worksheet.setStep10_CheckedBox_2(worksheetDetail.isStep10_CheckedBox_2());
		worksheet.setStep10_Time(worksheetDetail.getStep10_Time());
		worksheet.setStep10_ItemName(worksheetDetail.getStep10_ItemName());
		worksheet.setStep10_Supplier(worksheetDetail.getStep10_Supplier());
		worksheet.setStep10_mlChemicalInput(worksheetDetail.getStep10_mlChemicalInput());
		worksheet.setStep10_mlChemicalInput_2(worksheetDetail.getStep10_mlChemicalInput_2());
		worksheet.setStep10_CheckedBox_3(worksheetDetail.isStep10_CheckedBox_3());
		worksheet.setStep10_CheckedBox_4(worksheetDetail.isStep10_CheckedBox_4());

		// =================================== END =====================================

		// ============================= Section Eleven ================================

		worksheet.setStep11_Item_1(worksheetDetail.getStep11_Item_1());
		worksheet.setStep11_Supplier_1(worksheetDetail.getStep11_Supplier_1());
		worksheet.setStep11_LotNum(worksheetDetail.getStep11_LotNum());
		worksheet.setStep11_CheckedBox_1(worksheetDetail.isStep11_CheckedBox_1());

		// =================================== END =====================================

		// ============================= Section Twelve ================================

		worksheet.setStep12_Item_1(worksheetDetail.getStep12_Item_1());
		worksheet.setStep12_Supplier_1(worksheetDetail.getStep12_Supplier_1());
		worksheet.setStep12_LotNum(worksheetDetail.getStep12_LotNum());
		worksheet.setStep12_TimeInput(worksheetDetail.getStep12_TimeInput());
		worksheet.setStep12_CheckedBox_1(worksheetDetail.isStep12_CheckedBox_1());
		worksheet.setStep12_CheckedBox_2(worksheetDetail.isStep12_CheckedBox_2());
		worksheet.setStep12_Time(worksheetDetail.getStep12_Time());

		// =================================== END =====================================

		// ============================= Section Thirteen ==============================

		worksheet.setStep13_Item_1(worksheetDetail.getStep13_Item_1());
		worksheet.setStep13_Supplier_1(worksheetDetail.getStep13_Supplier_1());
		worksheet.setStep13_LotNum(worksheetDetail.getStep13_LotNum());
		worksheet.setStep13_CheckedBox_1(worksheetDetail.isStep13_CheckedBox_1());
		worksheet.setStep13_CheckedBox_2(worksheetDetail.isStep13_CheckedBox_2());

		// =================================== END =====================================

		Worksheet updateWorksheet = worksheetRepository.save(worksheet);
//    UserHistory userHistory = new UserHistory(
//                -1,
//                new java.util.Date(System.currentTimeMillis()),
//                updateWorksheet.getScientist(),
//                "Worksheet",
//                "Update Worksheet",
//                updateWorksheet.toString()
//    );
//    userHistoryRepo.save(userHistory);
		return ResponseEntity.ok(updateWorksheet);
	}

}
