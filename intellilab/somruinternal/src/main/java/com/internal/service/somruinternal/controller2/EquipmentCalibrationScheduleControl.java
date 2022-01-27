package com.internal.service.somruinternal.controller2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.EquipmentCalibrationSchedule;
import com.internal.service.somruinternal.repository2.EquipmentCalibrationScheduleRepository;

@RestController
@RequestMapping("/equipmentCalibrationSchedule")
public class EquipmentCalibrationScheduleControl extends ParentControl {

	private final static Logger LOGGER = LoggerFactory.getLogger(EquipmentCalibrationScheduleControl.class);

	@Autowired
	EquipmentCalibrationScheduleRepository equipmentCalibrationScheduleRepo;

	@GetMapping("/getAllEquipmentCalibrationSchedule")
	public List<EquipmentCalibrationSchedule> allEquipmentCalibrationSchedule() {
		LOGGER.info("getAllEquipmentCalibrationSchedule received request");
		List<EquipmentCalibrationSchedule> equipmentCalibrationSchedulelist = equipmentCalibrationScheduleRepo
				.findAll();
		LOGGER.info(
				String.format("Retrieved %d EquipmentCalibrationSchedule", equipmentCalibrationSchedulelist.size()));
		return equipmentCalibrationSchedulelist;
	}

	private EquipmentCalibrationSchedule loadEquipmentCalibrationSchedule(Long dbId) {
		EquipmentCalibrationSchedule equipmentCalibrationSchedule = equipmentCalibrationScheduleRepo
				.findByEquipmentCalibrationScheduleId(dbId);
		if (equipmentCalibrationSchedule == null) {
			LOGGER.info("equipmentCalibrationSchedule not found");
			throw new EntityNotFoundException(EquipmentCalibrationSchedule.class, "id", dbId.toString());
		}
		return equipmentCalibrationSchedule;
	}

	@PostMapping("/addEquipmentCalibrationSchedule")
	public EquipmentCalibrationSchedule saveEquipmentCalibrationSchedule(
			@RequestBody EquipmentCalibrationSchedule equipmentCalibrationSchedule) {

		LOGGER.info(String.format("addEquipmentCalibrationSchedule received request - details %s:",
				equipmentCalibrationSchedule));

		String action;
		String auditTrailRecord;
		if (equipmentCalibrationSchedule.getEquipmentCalibrationScheduleId() > 0) {
			EquipmentCalibrationSchedule previousEquipmentCalibrationScheduleDetails = this
					.loadEquipmentCalibrationSchedule(equipmentCalibrationSchedule.getEquipmentCalibrationScheduleId());
			action = "update EquipmentCalibrationSchedule";
			auditTrailRecord = getDiffs(equipmentCalibrationSchedule, previousEquipmentCalibrationScheduleDetails);
			LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		} else {
			action = "insert EquipmentCalibrationSchedule";
			auditTrailRecord = equipmentCalibrationSchedule.toString();
			LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		}

		EquipmentCalibrationSchedule savedEquipmentMaintSchedule = equipmentCalibrationScheduleRepo
				.save(equipmentCalibrationSchedule);
		super.saveUserHistory(equipmentCalibrationSchedule.getEditedBy(), "Equipment Calibration Schedule", action,
				auditTrailRecord);
		LOGGER.info("Completed saving EquipmentCalibrationSchedule");
		return savedEquipmentMaintSchedule;
	}

	private String getDiffs(EquipmentCalibrationSchedule lhs, EquipmentCalibrationSchedule rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException(
					"Error: Trying to compare two EquipmentCalibrationSchedule objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("EquipmentCalibrationSchedule GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@DeleteMapping("{id}")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> deleteEquipmentCalibrationSchedule(@PathVariable long id) {
		equipmentCalibrationScheduleRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
