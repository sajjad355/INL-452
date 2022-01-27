package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.error.SettingNotFoundException;
import com.internal.service.somruinternal.model2.ClientCompanyV2;
import com.internal.service.somruinternal.model2.SettingV2;
import com.internal.service.somruinternal.model2.SettingValueV2;
import com.internal.service.somruinternal.repository2.LoginRepositoryV2;
import com.internal.service.somruinternal.repository2.SettingRepositoryV2;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingv2")
public class SettingControlV2 extends ParentControl {
	@Autowired
	SettingRepositoryV2 settingRepo;

	@Autowired
	LoginRepositoryV2 loginRepository;

	private final static Logger LOGGER = LoggerFactory.getLogger(SettingControlV2.class);

	@GetMapping("/all")
	public List<SettingV2> getAllsettings() {
		LOGGER.info("getAllsettings received request");
		List<SettingV2> settingList = settingRepo.getAllSettingsOrderedByName();
		LOGGER.info(String.format("Retrieved %d setting rows", settingList.size()));
		return settingList;
	}

	@GetMapping("/{id}")
	public SettingV2 retrieveSetting(@PathVariable Long id) {
		Optional<SettingV2> setting = settingRepo.findById(id);

		if (!setting.isPresent())
			throw new SettingNotFoundException("Setting with ID " + id + " does not exist");

		return setting.get();
	}

	@GetMapping("/isExist")
	public ResponseEntity<Number> filterSettings(@RequestParam("name") String name) {
		List<SettingV2> settingWithName = settingRepo.findByName(name);

		return ResponseEntity.ok(settingWithName.size());
	}

	@PostMapping("")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> createSetting(@Valid @RequestBody SettingV2 data, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(handleError(errors));
		}

		String userName = this.getUserName();

		data.setEditedBy(userName);
		List<SettingValueV2> settingValues = data.getSettingValues();

		settingValues.forEach(settingValue -> {
			settingValue.setEditedBy(userName);
		});

		settingRepo.save(data);
		super.saveUserHistory(userName, "Setting", "Create Setting", data.toString());

		return ResponseEntity.ok(data);
	}

	@PutMapping("/{id}")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> updateSetting(@PathVariable long id, @Valid @RequestBody SettingV2 data, Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(handleError(errors));
		}

		String userName = this.getUserName();

		Optional<SettingV2> settingOptional = settingRepo.findById(id);

		if (!settingOptional.isPresent())
			return ResponseEntity.notFound().build();

		data.setSettingId(id);
		data.setEditedBy(userName);
		data.setName(data.getName());
		data.setDescription(data.getDescription());

		List<SettingValueV2> settingValues = data.getSettingValues();

		settingValues.forEach(settingValue -> {
			settingValue.setEditedBy(userName);
		});

		// System.out.println(data.getSettingValues() + " this is data setting values");
		settingRepo.save(data);
		super.saveUserHistory(userName, "Setting", "Edit Setting", data.toString());
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("{id}")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> deleteSetting(@PathVariable long id) {
		settingRepo.deleteById(id);
		String userName = this.getUserName();
		super.saveUserHistory(userName, "Setting", "Delete Setting", Long.toString(id));

		return ResponseEntity.ok().build();
	}

	private SettingV2 loadSetting(Long settingId) {
		SettingV2 setting = settingRepo.findBySettingId(settingId);
		if (setting == null) {
			LOGGER.info("setting not found");
			throw new EntityNotFoundException(SettingV2.class, "id", settingId.toString());
		}
		Hibernate.initialize(setting.getSettingValues());

		return setting;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public SettingV2 saveSetting(@Valid @RequestBody SettingV2 setting) {
		LOGGER.info(String.format("saveSetting received request - details %s:", setting));
		String action;
		String auditTrailRecord;

		if (setting.getSettingId() > 0) {
			SettingV2 previousSettingV2Details = this.loadSetting(setting.getSettingId());
			action = "update Setting";
			auditTrailRecord = getDiffs(setting, previousSettingV2Details);
		} else {
			action = "insert Setting";
			auditTrailRecord = setting.toString();
		}

		SettingV2 savedSetting = settingRepo.save(setting);
		LOGGER.info("Completed saving setting");
		super.saveUserHistory(setting.getEditedBy(), "Setting", action, auditTrailRecord);
		return savedSetting;
	}

	private String getDiffs(SettingV2 lhs, SettingV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two SettingV2 objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("SettingV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	private ArrayList<String> handleError(Errors errors) {

		List<FieldError> fieldErrors = errors.getFieldErrors();

		ArrayList<String> defaultErrors = new ArrayList<>();

		fieldErrors.forEach(error -> {
			defaultErrors.add(error.getDefaultMessage());
		});

		return defaultErrors;
	}

	private String getUserName() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = userDetails.getUsername();
		return loginRepository.searchSingleUserByEmail(userEmail).getName();
	}
}
