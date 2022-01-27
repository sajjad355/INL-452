package com.internal.service.somruinternal.controller2;

import java.util.List;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DataIntegrityViolationException;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.utils.SearchUtil;

@RestController
@RequestMapping("/sopv2")
public class SopControlV2 extends ParentControl {

	@Autowired
	SopRepositoryV2 sopRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(SopControlV2.class);

	@GetMapping("/allWithStatus/{status}")
	public List<SopV2> getAllSopsInStatus(@PathVariable(value = "status") String status) {
		LOGGER.info(String.format("getAllSopsInStatus - received status: %s", status));
		List<SopV2> sopList = sopRepo.findAllActiveInStatus(status);
		LOGGER.info(String.format("recipe count: %d", sopList.size()));
		return sopList;
	}

	@GetMapping("/all/{page}")
	public List<SopV2> getAllSopsByPage(@PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info("getAllSopsByPage");
		List<SopV2> sopList = sopRepo.findAllActiveByPage(pageable);
		LOGGER.info(String.format("recipe count: %d", sopList.size()));
		return sopList;
	}

	@GetMapping("/count")
	public Long getCount() {
		LOGGER.info("getCount");
		Long numRows = sopRepo.findAllActiveCount();
		LOGGER.info(String.format("count = %d", numRows));
		return numRows;
	}

	@GetMapping("/search/{searchKey}/{active}/{page}")
	public List<SopV2> searchSopPageable(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active, @PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(
				String.format("searchSopPageable received request with params searchKey = %s, active = %b, page = %d",
						searchKey, active, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<SopV2> sopList;
		if (active)
			sopList = sopRepo.searchActiveSops(search.toUpperCase(), pageable);
		else
			sopList = sopRepo.searchAllSops(search.toUpperCase(), pageable);
		LOGGER.info(String.format("Returned %d rows", sopList.size()));
		return sopList;
	}

	@GetMapping("/search/{searchKey}/{active}")
	public List<SopV2> searchSop(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(
				String.format("searchSop received request with params searchKey = %s, active = %b", searchKey, active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);

		List<SopV2> sopList;
		if (active)
			sopList = sopRepo.searchActiveSops(search.toUpperCase());
		else
			sopList = sopRepo.searchAllSops(search.toUpperCase());
		LOGGER.info(String.format("Returned %d rows", sopList.size()));
		return sopList;
	}

	@GetMapping("/searchCount/{searchKey}/{active}")
	public Long searchSopCount(@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "active") boolean active) {
		LOGGER.info(String.format("searchSopCount received request with params searchKey = %s, active = %b", searchKey,
				active));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long count;
		if (active)
			count = sopRepo.searchCountActive(search.toUpperCase());
		else
			count = sopRepo.searchCount(search.toUpperCase());
		LOGGER.info(String.format("Returned %d rows", count));
		return count;
	}

	/**
	 * Save the passed sop to the database.
	 * 
	 * @param sop the sop to insert or update
	 * @return the sop that was saved
	 */
	private SopV2 loadSop(Long sopId) {
		SopV2 sop = sopRepo.findBySopId(sopId);
		if (sop == null) {
			LOGGER.info("sop not found");
			throw new EntityNotFoundException(SopV2.class, "id", sopId.toString());
		}
		Hibernate.initialize(sop.getSopLinks());
		Hibernate.initialize(sop.getComponents());
		return sop;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public SopV2 saveSop(@Valid @RequestBody SopV2 sop) throws DataIntegrityViolationException {
		LOGGER.info(String.format("saveSop received request - details %s:", sop));
		// check to see no existing sops with same name and identifier

		Long count = sopRepo.validationCountCheck(sop.getSopId(), sop.getSopIdentifier(), sop.getName());
		if (count > 0)
			throw new DataIntegrityViolationException("Existing sop with same id and/or name");

		String action;
		String auditTrailRecord;
		if (sop.getSopId() > 0) {
			SopV2 previousSopDetails = this.loadSop(sop.getSopId());
			action = "update sop";
			auditTrailRecord = getDiffs(sop, previousSopDetails);
		} else {
			action = "insert sop";
			auditTrailRecord = sop.toString();
		}

		SopV2 savedSop = sopRepo.save(sop);
		LOGGER.info("Completed saving sop");
		super.saveUserHistory(sop.getEditedBy(), "SOP", "saveSop", savedSop.toString());
		return savedSop;
	}

	private String getDiffs(SopV2 lhs, SopV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Sop objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("SOpV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@GetMapping("/get/{id}")
	public ResponseEntity<SopV2> get(@PathVariable(value = "id") Long sopId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with sopId %d", sopId));
		if (sopId == null)
			throw new EntityNotFoundException(SopV2.class, "id", "");
		SopV2 sop = sopRepo.findBySopId(sopId);
		if (sop == null) {
			LOGGER.info("sop not found");
			throw new EntityNotFoundException(SopV2.class, "id", sopId.toString());
		}
		List<SopComponent> componentList = sop.getComponents(); // force components to load
		LOGGER.info(String.format("Retrieve %d components: ", componentList.size()));
		List<SopLinkV2> sopLinkList = sop.getSopLinks(); // force sop links to load
		LOGGER.info(String.format("Retrieve %d sop links: ", sopLinkList.size()));
		sopLinkList.forEach((sopLink) -> {
			SopV2 linkedSop = sopLink.getReferencedSop(); // force linked recipe to load
			LOGGER.info(String.format("loaded linked sop id: %d", linkedSop.getSopId()));
		});
		return ResponseEntity.ok().body(sop);
	}

}
