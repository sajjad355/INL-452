package com.internal.service.somruinternal.controller2;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.utils.SearchUtil;

@RestController
@RequestMapping("/kitv2")
public class KitControlV2 extends ParentControl {

	@Autowired
	KitRepositoryV2 kitRepo;

	@Autowired
	CatalogSequenceRepositoryV2 catalogSequenceRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(KitControlV2.class);

	@GetMapping("/count")
	public int getCount() {
		LOGGER.info("getCount received request");
		int count = kitRepo.findAll().size();
		LOGGER.info(String.format("retrievd %d kits", count));
		return count;
	}

	@GetMapping("/allWithEditStatus/{editStatus}")
	public List<KitV2> getAllKitsInEditStatus(@PathVariable(value = "editStatus") String editStatus) {
		LOGGER.info(String.format("getAllKitsInEditStatus - received editStatus: %s", editStatus));
		List<KitV2> kitList = kitRepo.findAllActiveInEditStatus(editStatus);
		LOGGER.info(String.format("kit count: %d", kitList.size()));
		return kitList;
	}

	@GetMapping("/all/{page}")
	public List<KitV2> getAllKitsByPage(@PathVariable(value = "page") int page) {
		LOGGER.info(String.format("getAllKitsByPage received request with parameter %d", page));
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("salesItemId").descending());
		// List<KitV2> kitList = kitRepo.findAll(new PageRequest(
		// page, 10, Sort.Direction.DESC, "salesItemId")).getContent();

		List<KitV2> kitList = kitRepo.findAll(pageable).getContent();

		LOGGER.info(String.format("retrievd %d kits", kitList.size()));
		return kitList;
	}

	@GetMapping("/search/{searchKey}/{active}/{page}")
	public List<KitV2> searchKitPageable(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("searchKitPageable received request with params searchKey = %s, active = %b",
				searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<KitV2> kitList;
		if (active)
			kitList = kitRepo.searchActiveKits(search.toUpperCase(), pageable);
		else
			kitList = kitRepo.searchAllKits(search.toUpperCase(), pageable);
		LOGGER.info(String.format("Returned %d rows", kitList.size()));
		return kitList;
	}

	@GetMapping("/search/{searchKey}")
	public List<KitV2> searchKit(@PathVariable(value = "searchKey") String searchKey) {
		LOGGER.info(String.format("searchKit received request with params searchKey = %s", searchKey));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<KitV2> kitList = kitRepo.searchActiveKits(search.toUpperCase());
		LOGGER.info(String.format("Returned %d rows", kitList.size()));
		return kitList;
	}

	@GetMapping("/searchCount/{searchKey}/{active}")
	public Long searchKitCount(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(String.format("searchKitCount received request with params searchKey = %s, active = %b", searchKey,
				active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active)
			count = kitRepo.searchCountActive(search.toUpperCase());
		else
			count = kitRepo.searchCount(search.toUpperCase());
		LOGGER.info(String.format("Returned %d rows", count));
		return count;
	}

	/**
	 * Save the passed kit to the database.
	 * 
	 * @param kit the kit to insert or update
	 * @return the kit that was saved
	 */

	private KitV2 loadKit(Long kitId) {
		KitV2 kit = kitRepo.findBySalesItemId(kitId);
		if (kit == null) {
			LOGGER.info("kit not found");
			throw new EntityNotFoundException(KitV2.class, "id", kitId.toString());
		}
		Hibernate.initialize(kit.getKitComponents());
		return kit;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public KitV2 saveKit(@Valid @RequestBody KitV2 kit) throws DataIntegrityViolationException {
		LOGGER.info(String.format("saveKit received request - details %s:", kit));
		String action;
		String auditTrailRecord;

		// check to see no existing kit with same name and identifier
		Long count = kitRepo.validationCountCheck(kit.getSalesItemId(), kit.getCatalogNumber(), kit.getName());
		if (count > 0)
			throw new DataIntegrityViolationException("Existing kit with same catalog number and/or name");

		if (kit.getSalesItemId() > 0) {
			KitV2 previousKitDetails = this.loadKit(kit.getSalesItemId());
			action = "update kit";
			auditTrailRecord = getDiffs(kit, previousKitDetails);
		} else {
			action = "insert kit";
			auditTrailRecord = kit.toString();
		}

		KitV2 savedKit = kitRepo.save(kit);
		LOGGER.info("Completed saving kit");
		super.saveUserHistory(kit.getEditedBy(), "KIT", "saveKit", savedKit.toString());
		return savedKit;
	}

	private String getDiffs(KitV2 lhs, KitV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Kit objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("KitV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@GetMapping("/get/{id}")
	public ResponseEntity<KitV2> get(@PathVariable(value = "id") Long kitId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with kitId %d", kitId));
		if (kitId == null)
			throw new EntityNotFoundException(KitV2.class, "id", "");
		Optional<KitV2> kit = kitRepo.findById(kitId);
		if (kit.isEmpty()) {
			LOGGER.info("kit not found");
			throw new EntityNotFoundException(KitV2.class, "id", kitId.toString());
		}
		List<KitComponentV2> componentList = kit.get().getKitComponents(); // force components to load
		LOGGER.info(String.format("Retrieve %d components: ", componentList.size()));
		return ResponseEntity.ok().body(kit.orElse(null));
	}

}
