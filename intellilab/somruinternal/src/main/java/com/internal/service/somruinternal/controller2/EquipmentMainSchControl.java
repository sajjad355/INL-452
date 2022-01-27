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
import com.internal.service.somruinternal.model2.EquipmentMaintSchedule;
import com.internal.service.somruinternal.repository2.EquipmentMaintScheduleRepository;

@RestController
@RequestMapping("/equipmentMaintSch")
public class EquipmentMainSchControl extends ParentControl {

	private final static Logger LOGGER = LoggerFactory.getLogger(EquipmentMainSchControl.class);

	@Autowired
	EquipmentMaintScheduleRepository equipmentMaintScheduleRepo;

	@GetMapping("/getAllEquipmentMaintSch")
	public List<EquipmentMaintSchedule> allEquipmentMaintSch() {
		LOGGER.info("getAllEquipmentMaintSch received request");
		List<EquipmentMaintSchedule> equipmentMaintSchedulelist = equipmentMaintScheduleRepo.findAll();
		LOGGER.info(String.format("Retrieved %d EquipmentMaintSchedule", equipmentMaintSchedulelist.size()));
		return equipmentMaintSchedulelist;
	}

	private EquipmentMaintSchedule loadEquipmentMaintSchedule(Long dbId) {
		EquipmentMaintSchedule equipmentMaintSchedule = equipmentMaintScheduleRepo.findByEquipmentMaintScheduleId(dbId);
		if (equipmentMaintSchedule == null) {
			LOGGER.info("equipmentMaintSchedule not found");
			throw new EntityNotFoundException(EquipmentMaintSchedule.class, "id", dbId.toString());
		}
		return equipmentMaintSchedule;
	}

	@PostMapping("/addEquipmentMaintSchedule")
	public EquipmentMaintSchedule saveEquipmentMaintSchedule(
			@RequestBody EquipmentMaintSchedule equipmentMaintSchedule) {

		LOGGER.info(String.format("addEquipmentMaintSchedule received request - details %s:", equipmentMaintSchedule));

		String action;
		String auditTrailRecord;
		if (equipmentMaintSchedule.getEquipmentMaintScheduleId() > 0) {
			EquipmentMaintSchedule previousEquipmentMaintScheduleDetails = this
					.loadEquipmentMaintSchedule(equipmentMaintSchedule.getEquipmentMaintScheduleId());
			action = "update EquipmentMaintSchedule";
			auditTrailRecord = getDiffs(equipmentMaintSchedule, previousEquipmentMaintScheduleDetails);
			LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		} else {
			action = "insert equipmentMaintSchedule";
			auditTrailRecord = equipmentMaintSchedule.toString();
			LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		}

		EquipmentMaintSchedule savedEquipmentMaintSchedule = equipmentMaintScheduleRepo.save(equipmentMaintSchedule);
		super.saveUserHistory(equipmentMaintSchedule.getEditedBy(), "Equipment Maint Schedule", action,
				auditTrailRecord);
		LOGGER.info("Completed saving EquipmentMaintSchedule");
		return savedEquipmentMaintSchedule;
	}

	private String getDiffs(EquipmentMaintSchedule lhs, EquipmentMaintSchedule rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException(
					"Error: Trying to compare two EquipmentMaintSchedule objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("EquipmentMaintSchedule GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@DeleteMapping("{id}")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> deleteEquipmentMaintSchedule(@PathVariable long id) {
		equipmentMaintScheduleRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
