package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.EquipmentV2;
import com.internal.service.somruinternal.model2.ClientCompanyV2;
import com.internal.service.somruinternal.model2.EquipmentActivityLogV2;
import com.internal.service.somruinternal.repository2.EquipmentRepositoryV2;
import com.internal.service.somruinternal.utils.SearchUtil;

@RestController
@RequestMapping("/equipmentV2")
public class EquipmentControlV2 extends ParentControl {

	@Autowired
	EquipmentRepositoryV2 eqRepositoryV2;

	private final static Logger LOGGER = LoggerFactory.getLogger(EquipmentControlV2.class);

	@GetMapping("/getAllEquipment")
	public List<EquipmentV2> getAllEquipment() {
		LOGGER.info("getAllEquipment received request");
		List<EquipmentV2> equipmentList = eqRepositoryV2.findAll();
		LOGGER.info(String.format("Retrieved %d equipments", equipmentList.size()));

		return equipmentList;
	}

	private EquipmentV2 loadEquipment(Long equipmentId) {
		EquipmentV2 equipment = eqRepositoryV2.findByEquipmentId(equipmentId);

		if (equipment == null) {
			LOGGER.info("equipment not found");
			throw new EntityNotFoundException(ClientCompanyV2.class, "id", equipmentId.toString());
		}
		Hibernate.initialize(equipment.getEquipmentActivityLogs());
		return equipment;
	}

	// Add and update will perform by one api
	@PostMapping("/addEquipment2")
	public EquipmentV2 addEquipment(@RequestBody EquipmentV2 equipmentv2) {
		LOGGER.info(String.format("addEquipment received request - details %s:", equipmentv2));
		String action;
		String auditTrailRecord;

		if (equipmentv2.getEquipmentId() != null && equipmentv2.getEquipmentId() > 0) {
			EquipmentV2 previousEquipmentDetails = this.loadEquipment(equipmentv2.getEquipmentId());
			action = "update client";
			auditTrailRecord = getDiffs(equipmentv2, previousEquipmentDetails);
		} else {
			action = "insert client";
			auditTrailRecord = equipmentv2.toString();
		}

		this.addEquipmentActivityLog(equipmentv2);
		equipmentv2 = eqRepositoryV2.save(equipmentv2);
		super.saveUserHistory(equipmentv2.getEditedBy(), "Equipment", action, auditTrailRecord);
		LOGGER.info("Completed saving equipment");
		return equipmentv2;
	}

	private String getDiffs(EquipmentV2 lhs, EquipmentV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Equipment objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("EquipmentV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	private void addEquipmentActivityLog(EquipmentV2 equipmentv2) {
		LOGGER.info("addEquipmentActivityLog called");
		EquipmentV2 currentEquipmentRecord = null;
		// update case is first - non zero positive id
		if (equipmentv2.getEquipmentId() != null && equipmentv2.getEquipmentId() > 0) {
			LOGGER.info("update equipment activity log on equipment update");
			currentEquipmentRecord = eqRepositoryV2.findByEquipmentId(equipmentv2.getEquipmentId());
			if (currentEquipmentRecord == null) {
				LOGGER.info("equipment not found");
				throw new EntityNotFoundException(EquipmentV2.class, "id= " + equipmentv2.getEquipmentId());
			}
			equipmentv2.setEquipmentActivityLogs(currentEquipmentRecord.getEquipmentActivityLogs());
			// compare current equipment dates with received equipment dates and see if we
			// need to
			// update equipment activity log
			Date currentCalibrationDate = currentEquipmentRecord.getCalibrationPerformed();
			Date saveCalibrationDate = equipmentv2.getCalibrationPerformed();
			Date currentDate = new Date();
			if (currentCalibrationDate != null) {
				LOGGER.info("Current Calibration Date: " + currentCalibrationDate.getTime());
				// compare to received equipment calibration date and create new log record if
				// different
				if ((saveCalibrationDate != null)
						&& (currentCalibrationDate.getTime() != saveCalibrationDate.getTime())) {
					LOGGER.info("Updated Calibration Date: " + saveCalibrationDate.getTime());
					EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
					activityLog.setActivityDate(saveCalibrationDate);
					activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
					activityLog.setActivityType("CALIBRATION");
					activityLog.setModifiedOn(currentDate);
					equipmentv2.getEquipmentActivityLogs().add(activityLog);
				}
			} else {
				// no current calibration date - check if one was provided and create new log
				// record if so
				if (saveCalibrationDate != null) {
					LOGGER.info("No Current Calibration Date, updated Calibration Date: " + saveCalibrationDate);
					EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
					activityLog.setActivityDate(saveCalibrationDate);
					activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
					activityLog.setActivityType("CALIBRATION");
					activityLog.setModifiedOn(currentDate);
					equipmentv2.getEquipmentActivityLogs().add(activityLog);
				}
			}

			Date currentMaintenanceDate = currentEquipmentRecord.getLastMaintenancePerformedDate();
			Date saveMaintenanceDate = equipmentv2.getLastMaintenancePerformedDate();
			if (currentMaintenanceDate != null) {
				LOGGER.info("Current Maintenance Date: " + currentMaintenanceDate.getTime());
				// compare to received equipment maint date and create new log record if
				// different
				if ((saveMaintenanceDate != null)
						&& (currentMaintenanceDate.getTime() != saveMaintenanceDate.getTime())) {
					LOGGER.info("Updated Maintenance Date: " + saveMaintenanceDate.getTime());
					EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
					activityLog.setActivityDate(saveMaintenanceDate);
					activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
					activityLog.setActivityType("MAINTENANCE");
					activityLog.setModifiedOn(currentDate);
					equipmentv2.getEquipmentActivityLogs().add(activityLog);
				}
			} else {
				// no current calibration date - check if one was provided and create new log
				// record if so
				if (saveMaintenanceDate != null) {
					LOGGER.info("No Current Maintenance Date, updated Maintenance Date: " + saveMaintenanceDate);
					EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
					activityLog.setActivityDate(saveMaintenanceDate);
					activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
					activityLog.setActivityType("MAINTENANCE");
					activityLog.setModifiedOn(currentDate);
					equipmentv2.getEquipmentActivityLogs().add(activityLog);
				}
			}

		} else {
			LOGGER.info("update equipment activity log on equipment insert");
			// insert case
			Date currentDate = new Date();
			Date saveCalibrationDate = equipmentv2.getCalibrationPerformed();
			if (saveCalibrationDate != null) {
				LOGGER.info("Initial Calibration Date: " + saveCalibrationDate);
				EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
				activityLog.setActivityDate(saveCalibrationDate);
				activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
				activityLog.setActivityType("CALIBRATION");
				activityLog.setModifiedOn(currentDate);
				equipmentv2.getEquipmentActivityLogs().add(activityLog);
			}
			Date saveMaintenanceDate = equipmentv2.getLastMaintenancePerformedDate();
			if (saveMaintenanceDate != null) {
				LOGGER.info("Initial Maintenance Date: " + saveMaintenanceDate);
				EquipmentActivityLogV2 activityLog = new EquipmentActivityLogV2();
				activityLog.setActivityDate(saveMaintenanceDate);
				activityLog.setActivityPerformedBy(equipmentv2.getEditedBy());
				activityLog.setActivityType("MAINTENANCE");
				activityLog.setModifiedOn(currentDate);
				equipmentv2.getEquipmentActivityLogs().add(activityLog);
			}
		}
	}

	@GetMapping("/getOneEquipment2")
	public EquipmentV2 getOneEquipment(@RequestParam(value = "id") long equipmentId) {
		LOGGER.info(String.format("get received request with equipmentId %d", equipmentId));
		EquipmentV2 equipmentv2 = eqRepositoryV2.findByEquipmentId(equipmentId);
		if (equipmentv2 == null) {
			LOGGER.info("equipment not found");
			throw new EntityNotFoundException(EquipmentV2.class, "id= " + equipmentId);
		}
		LOGGER.info(String.format("get returned following equipment details: %s", equipmentv2.toString()));
		int logCount = equipmentv2.getEquipmentActivityLogs().size();
		LOGGER.info(String.format("Equipment has %d log records", logCount));
		return equipmentv2;
	}

	@GetMapping("/allActiveEquipment2")
	public List<EquipmentV2> getAllActiveEquipment(@RequestParam(value = "active") boolean active,
			@RequestParam(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("equipmentId").descending());
		LOGGER.info(String.format("getAllActiveEquipments with param page = %d", page));

		Page<EquipmentV2> equipmentPage = eqRepositoryV2.findByActive(active, pageable);

		LOGGER.info(String.format("Retrieved %d equipments", equipmentPage.getSize()));

		List<EquipmentV2> equipmentList = new ArrayList<EquipmentV2>();

		if (equipmentPage != null) {
			equipmentList = equipmentPage.getContent();
		}

		return equipmentList;
	}

	@GetMapping("/searchEquipment2")
	public List<EquipmentV2> searchPageable(@RequestParam(value = "searchKey") String searchKey,
			@RequestParam(value = "active") boolean active, @RequestParam(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("searchPageable received request with searchKey %s, active %b, page %d", searchKey,
				active, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		List<EquipmentV2> equipmentList;
		if (active) {
			equipmentList = eqRepositoryV2.searchActive(search.toUpperCase(), pageable);
		} else {
			equipmentList = eqRepositoryV2.searchInactive(search.toUpperCase(), pageable);
		}
		LOGGER.info(String.format("Retrieved %d equipments", equipmentList.size()));

		return equipmentList;
	}

	@GetMapping("/searchCount")
	public Long searchCount(@RequestParam(value = "searchKey") String searchKey,
			@RequestParam(value = "active") boolean active) {
		LOGGER.info(String.format("searchCount received request with searchKey %s, active %b", searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active) {
			count = eqRepositoryV2.searchCountActive(search.toUpperCase());
		} else {
			count = eqRepositoryV2.searchCount(search.toUpperCase());
		}
		LOGGER.info(String.format("Retrieved %d equipments", count));
		return count;
	}

}
