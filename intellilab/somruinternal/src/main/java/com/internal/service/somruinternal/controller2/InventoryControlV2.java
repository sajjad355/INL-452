package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.InventoryRepositoryV2;
import com.internal.service.somruinternal.repository2.LocationRepositoryV2;
import com.internal.service.somruinternal.utils.SearchUtil;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;

@RestController
@RequestMapping("/inventoryV2")
public class InventoryControlV2 extends ParentControl {

	private InventoryRepositoryV2 inventoryRepository;

	private LocationRepositoryV2 locationRepositiory;

	private final static Logger LOGGER = LoggerFactory.getLogger(InventoryControlV2.class);

	@Autowired
	public InventoryControlV2(InventoryRepositoryV2 inventoryRepo, LocationRepositoryV2 locationRepositiory) {
		super();
		this.inventoryRepository = inventoryRepo;
		this.locationRepositiory = locationRepositiory;
	}

	// Does not return inventory details - call getItemByIdentifier for that purpose
	// Everything that is known about that inventory item will be loaded
	@GetMapping("/loadAll")
	public List<InventoryV2> loadAllActiveItems() {
		LOGGER.info("Now entering loadAllActiveItems");
		List<InventoryV2> items = inventoryRepository.findAllActiveOnly();
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/all/{page}")
	public List<InventoryV2> getAllItemsByPage(@PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("Now entering getAllItemsByPage(%d)...", page));

		List<InventoryV2> items = inventoryRepository.findAllActiveOnly(pageable);
		items.forEach((item) -> {
			loadAssociations(item, false);
		});
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/count")
	public Long getCount() {
		LOGGER.info("getCount");
		Long numRows = inventoryRepository.countDistinctByActiveTrue();
		LOGGER.info(String.format("count = %d", numRows));
		return numRows;
	}

	@GetMapping("/search/all/{key}")
	public List<InventoryV2> search(@PathVariable(value = "key") String key) {
		LOGGER.info(String.format("Now entering search(%s)...", key));

		String sqlKey = "";
		if (key != null) {
			sqlKey = SearchUtil.replaceIllegalSearchCharacters(key).toUpperCase();
		}
		sqlKey = "%" + sqlKey + "%";

		List<InventoryV2> items = inventoryRepository.searchByActiveOnlyAndContainsKey(sqlKey);
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/loadLowInStock")
	public List<InventoryV2> loadLowInStock() {
		LOGGER.info("Now entering loadLowInStock...");
		List<InventoryV2> items = inventoryRepository.loadLowInStock();
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/searchByNameAndCatalogNumberExactMatch/{name}/{catalogNumber}")
	public List<InventoryV2> searchByNameAndCatalogNumberExactMatch(@PathVariable(value = "name") String name,
			@PathVariable(value = "catalogNumber") String catalogNumber) {
		LOGGER.info(String.format("Now entering searchByNameAndCatalogNumberExactMatch - name =%s, catalogNumber = %s",
				name, catalogNumber));

		String searchName = "";
		if (name != null) {
			searchName = SearchUtil.replaceIllegalSearchCharacters(name);
		}
		String searchCatalogNumber = "";
		if (catalogNumber != null) {
			searchCatalogNumber = SearchUtil.replaceIllegalSearchCharacters(catalogNumber);
		}
		List<InventoryV2> items = inventoryRepository.searchByNameAndCatalogNumberExactMatch(searchName,
				searchCatalogNumber);
		LOGGER.info(String.format("Returned %d rows", items.size()));
		for (InventoryV2 item : items) {
			// we only expect 1 row back from this query at most so we populate all the
			// child elements
			// of the inventory entity so it can sent back in an update if needed.
			loadAssociations(item, true);
		}
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/search/{criterion}/{key}/{page}/{activeOnly}")
	public List<InventoryV2> searchForCriterionByPage(@PathVariable(value = "criterion") String criterion,
			@PathVariable(value = "key") String key, @PathVariable(value = "page") int page,
			@PathVariable(value = "activeOnly") boolean activeOnly) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("Now entering searchForCriterionByPage(%s, %s, %d, %b)...", criterion, key, page,
				activeOnly));
		List<InventoryV2> items;

		String sqlKey = "";
		if (key != null) {
			sqlKey = SearchUtil.replaceIllegalSearchCharacters(key).toUpperCase();
		}

		switch (criterion) {
		case "like":
			sqlKey = "%" + sqlKey + "%";
			LOGGER.info("like search");
			if (activeOnly) {
				items = inventoryRepository.searchByActiveOnlyAndContainsKeyPageable(sqlKey, pageable);
			} else {
				items = inventoryRepository.searchAllAndContainsKeyPageable(sqlKey, pageable);
			}
			break;
		case "location":
			LOGGER.info("location search");
			long id;
			try {
				id = Long.parseLong(sqlKey);
			} catch (Exception e) {
				id = 0L;
			}
			if (activeOnly) {
				items = inventoryRepository.searchByLocationIdActiveOnlyPageable(id, pageable);
			} else {
				items = inventoryRepository.searchByLocationIdPageable(id, pageable);
			}
			break;
		case "type":
			LOGGER.info("type search");
			if (activeOnly) {
				items = inventoryRepository.searchByTypeActiveOnlyPageable(sqlKey, pageable);
			} else {
				items = inventoryRepository.searchAllByTypePageable(sqlKey, pageable);
			}
			break;
		default:
			items = new ArrayList<>();
			LOGGER.info(String.format("criterion %s not recognized - returning empty list", criterion));
			break;
		}
		items.forEach((item) -> {
			loadAssociations(item, false);
		});
		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/search/locationAndSubLocation/{locationId}/{subLocationId}/{page}/{activeOnly}")
	public List<InventoryV2> searchForLocationAndSubLocationByPage(@PathVariable(value = "locationId") Long locationId,
			@PathVariable(value = "subLocationId") Long subLocationId, @PathVariable(value = "page") int page,
			@PathVariable(value = "activeOnly") boolean activeOnly) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
		LOGGER.info(String.format("Now entering searchForLocationAndSubLocationByPage(%d, %d, %d, %b)...", locationId,
				subLocationId, page, activeOnly));
		List<InventoryV2> items;
		if (activeOnly) {
			items = inventoryRepository.searchByLocationIdAndSublocationIdActiveOnly(locationId, subLocationId,
					pageable);
		} else {
			items = inventoryRepository.searchByLocationIdAndSublocationIdPageable(locationId, subLocationId, pageable);
		}
		items.forEach((item) -> {
			loadAssociations(item, false);
		});

		LOGGER.info(String.format("Returning %d rows", items.size()));
		return items;
	}

	@GetMapping("/count/{criterion}/{key}/{activeOnly}")
	public Long getCountForCriterion(@PathVariable(value = "criterion") String criterion,
			@PathVariable(value = "key") String key, @PathVariable(value = "activeOnly") boolean activeOnly) {
		LOGGER.info(String.format("Now entering getCountForCriterion(%s, %s, %b)...", criterion, key, activeOnly));
		Long count;
		String sqlKey = "";
		if (key != null) {
			sqlKey = SearchUtil.replaceIllegalSearchCharacters(key).toUpperCase();
		}
		switch (criterion) {
		case "like":
			sqlKey = "%" + sqlKey + "%";
			if (activeOnly) {
				count = inventoryRepository.countByActiveOnlyAndContainsKey(sqlKey);
			} else {
				count = inventoryRepository.countAllAndContainsKey(sqlKey);
			}
			break;
		case "location":
			long id;
			try {
				id = Long.parseLong(sqlKey);
			} catch (Exception e) {
				id = 0L;
			}
			if (activeOnly) {
				count = inventoryRepository.countByLocationIdActiveOnly(id);
			} else {
				count = inventoryRepository.countByLocationId(id);
			}
			break;
		case "type":
			if (activeOnly) {
				count = inventoryRepository.countDistinctByActiveTrueAndType(sqlKey);
			} else {
				count = inventoryRepository.countDistinctByType(sqlKey);
			}
			break;
		case "catalogNumber":
			if (activeOnly) {
				count = inventoryRepository.countDistinctByActiveTrueAndCatalogNumber(sqlKey);
			} else {
				count = inventoryRepository.countDistinctByCatalogNumber(sqlKey);
			}
			break;
		default:
			count = 0L;
			break;
		}
		LOGGER.info(String.format("Retrieved %d rows", count));
		return count;
	}

	@GetMapping("/count/locationAndSubLocation/{locationId}/{subLocationId}/{activeOnly}")
	public Long getCountForLocationAndSubLocation(@PathVariable(value = "locationId") Long locationId,
			@PathVariable(value = "subLocationId") Long subLocationId,
			@PathVariable(value = "activeOnly") boolean activeOnly) {
		LOGGER.info(String.format("Now entering getCountForLocationAndSubLocation(%d, %d, %b)...", locationId,
				subLocationId, activeOnly));
		Long count;
		if (activeOnly) {
			count = inventoryRepository.countByLocationIdAndSublocationIdActiveOnly(locationId, subLocationId);
		} else {
			count = inventoryRepository.countByLocationIdAndSublocationId(locationId, subLocationId);
		}
		LOGGER.info(String.format("Retrieved %d rows", count));
		return count;
	}

	@GetMapping("/catalogNumberValidator/{inventoryId}/{catalogNumber}")
	public boolean validateCatalogNumber(@PathVariable(value = "inventoryId") long inventoryId,
			@PathVariable(value = "catalogNumber") String catalogNumber) {
		return inventoryRepository.countDistinctByInventoryIdIsNotAndCatalogNumberIs(inventoryId, catalogNumber) == 0;
	}

	@GetMapping("/nameValidator/{inventoryId}/{catalogNumber}/{supplierCatalogNumber}/{unit}/{containerSize}")
	public boolean validateName(@PathVariable(value = "inventoryId") long inventoryId,
			@PathVariable(value = "catalogNumber") String catalogNumber,
			@PathVariable(value = "supplierCatalogNumber") String supplierCatalogNumber,
			@PathVariable(value = "unit") String unit, @PathVariable(value = "containerSize") Double containerSize) {
		return inventoryRepository
				.countDistinctByInventoryIdIsNotAndCatalogNumberAndSupplierCatalogNumberAndUnitAndContainerSize(
						inventoryId, catalogNumber, supplierCatalogNumber, unit, containerSize) == 0;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<InventoryV2> getItemByIdentifier(@PathVariable(value = "id") Long id) {
		LOGGER.info(String.format("Now entering getItemByIdentifier(%s)...", id));
		InventoryV2 result = inventoryRepository.findByInventoryId(id);
		if (result == null) {
			return ResponseEntity.notFound().build();
		}
		// load details
		loadAssociations(result, true);
		return ResponseEntity.ok().body(result);
	}

	private InventoryV2 loadInventory(Long inventoryId) {
		InventoryV2 inventory = inventoryRepository.findByInventoryId(inventoryId);
		if (inventory == null) {
			LOGGER.info("inventory not found");
			throw new EntityNotFoundException(InventoryV2.class, "id", inventoryId.toString());
		}
		Hibernate.initialize(inventory.getInventoryDetails());
		return inventory;
	}

	@Transactional
	@PostMapping("/save")
	public InventoryV2 save(@Valid @RequestBody InventoryV2 item) {
		LOGGER.info(String.format("InventoryControlV2 save received requested with Inventory Item %s", item));

		String action;
		String auditTrailRecord;
		if (item.getInventoryId() > 0) {
			InventoryV2 previousInventoryDetails = this.loadInventory(item.getInventoryId());
			action = "update Inventory";
			auditTrailRecord = getDiffs(item, previousInventoryDetails);
		} else {
			action = "insert Inventory";
			auditTrailRecord = item.toString();
		}

		InventoryV2 savedItem = inventoryRepository.save(item);
		LOGGER.info(String.format("Saving User history with details of saved item %s", savedItem));
		super.saveUserHistory(item.getEditedBy(), "Inventory", "Created an item", savedItem.toString());
		return savedItem;
	}

	private String getDiffs(InventoryV2 lhs, InventoryV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Inventory objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("InventoryV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	private void loadAssociations(InventoryV2 item, boolean loadDetailAssociations) {
		List<InventoryDetailV2> details = item.getInventoryDetails();
		LOGGER.info(String.format("Loaded %d detail rows", details.size()));
		if (loadDetailAssociations) {
			for (InventoryDetailV2 detail : details) {
				LocationV2 location = detail.getLocation();
				LOGGER.info(String.format("Inventory Location %s", location));
				LocationV2 subLocation = detail.getSubLocation();
				LOGGER.info(String.format("Inventory Sub-location %s", subLocation));
				List<InventoryDetailVialV2> vialDetails = detail.getInventoryDetailVials();
				LOGGER.info(String.format("Loaded %d vial detail rows", vialDetails.size()));
			}
		}
	}

	/**
	 * @param inventoryId             - parent inventory id of the detail record we
	 *                                are checking out
	 * @param inventoryDetailId       - id of the detail record we are checking out
	 * @param checkoutType            - 'Unopened', 'Inuse' are only allowed values
	 * @param checkoutPurpose         - 'Use', 'Move', 'Ship' are only allowed
	 *                                values
	 * @param checkoutUserName        - username of user performing the checkout
	 * @param requestedCheckoutAmount - checkout amount. Must be greater than 0. If
	 *                                checkout type is 'Unopened', checkout amount
	 *                                must not be greater than detail total amount.
	 *                                If checkout type is 'Inuse', checkout amount
	 *                                must not be greater than detail number in use.
	 * @param checkoutLocationId      - the location id to where the item will be
	 *                                checkout to. Required if checkout purpose is
	 *                                'Move'. Provide 0 if N/A
	 * @param checkoutSubLocationId   - the sub location id to where the item will
	 *                                be checkout to. As not every location has a
	 *                                sublocation, is optional even in case of
	 *                                'Move'. . Provide 0 if N/A
	 * @param checkoutPurposeReason   - user given reason why the checkout has been
	 *                                performed
	 * @return - InventoryV2 - the updated Inventory with all of it's child items
	 *         including the the child items updated by the act of checking out.
	 */
	@Transactional
	@PostMapping("/checkout")
	public InventoryV2 checkoutItem(@Valid @RequestBody InventoryV2 item, @RequestParam long inventoryDetailId,
			@RequestParam String checkoutType, @RequestParam String checkoutPurpose,
			@RequestParam String checkoutUserName, @RequestParam int requestedCheckoutAmount,
			@RequestParam long checkoutLocationId, @RequestParam long checkoutSubLocationId,
			@RequestParam String checkoutPurposeReason) {
		InventoryV2 inventory = null;
		LOGGER.info("checkoutItem received request - params follow:");
		LOGGER.info(String.format("inventoryDetailId: %d", inventoryDetailId));
		LOGGER.info(String.format("inventory: %s", item));
		LOGGER.info(String.format("checkoutType: %s", checkoutType));
		LOGGER.info(String.format("checkoutPurpose: %s", checkoutPurpose));
		LOGGER.info(String.format("checkoutUserName: %s", checkoutUserName));
		LOGGER.info(String.format("requestedCheckoutAmount: %d", requestedCheckoutAmount));
		LOGGER.info(String.format("checkoutLocationId: %d", checkoutLocationId));
		LOGGER.info(String.format("checkoutSubLocationId: %d", checkoutSubLocationId));
		LOGGER.info(String.format("checkoutPurposeReason: %s", checkoutPurposeReason));

		inventory = inventoryRepository.findByInventoryId(item.getInventoryId());
		if (inventory == null) {
			throw new EntityNotFoundException(InventoryV2.class, "id", Long.toString(item.getInventoryId()));
		}
		InventoryDetailV2 checkoutItem = null;
		for (InventoryDetailV2 id : inventory.getInventoryDetails()) {
			if (id.getInventoryDetailId() == inventoryDetailId)
				checkoutItem = id;
		}
		if (checkoutItem == null) {
			throw new EntityNotFoundException(InventoryDetailV2.class, "id", Long.toString(inventoryDetailId));
		}
		int currentCheckoutAmt = checkoutItem.getAmount() == null ? 0 : checkoutItem.getAmount();
		int currentNumberInUse = checkoutItem.getNumberInUse() == null ? 0 : checkoutItem.getNumberInUse();

		if (!"Use".equals(checkoutPurpose) && !"Move".equals(checkoutPurpose) && !"Ship".equals(checkoutPurpose)) {
			throw new IllegalArgumentException("checkoutPurpose must be 'Use', 'Move' or 'Ship'");
		}

		if (!"Ship".equals(checkoutPurpose)) {
			if (!"Inuse".equals(checkoutType) && !"Unopened".equals(checkoutType)) {
				throw new IllegalArgumentException("checkoutType must be 'Inuse' or 'Unopened'");
			}
			if (requestedCheckoutAmount <= 0) {
				throw new IllegalArgumentException("requestedCheckoutAmount must be greater than 0");
			}
			if ("Unopened".equals(checkoutType)) {
				if (requestedCheckoutAmount > currentCheckoutAmt) {
					throw new IllegalArgumentException("requestedCheckoutAmount exceeds available amount.");
				}
				if (("Move".equals(checkoutPurpose)) && checkoutLocationId == 0) {
					throw new IllegalArgumentException(
							"checkoutLocationId is required when checkoutType='Unopened' and checkoutPurpose='Move'.");
				}
			} else if ("Inuse".equals(checkoutType)) {
				if (requestedCheckoutAmount > currentNumberInUse) {
					throw new IllegalArgumentException("requestedCheckoutAmount exceeds available amount.");
				}
			}
		}
		if ((checkoutUserName == null) || "".equals(checkoutUserName)) {
			throw new IllegalArgumentException("checkoutUserName is required");
		}

		LOGGER.info("Validated data successfully - now perform the checkout");
		java.util.Date today = new java.util.Date();

		if ("Move".equals(checkoutPurpose)) {
			if ("Unopened".equals(checkoutType)) {
				LOGGER.info("Perform 'Unopened' plus 'Move' checkout logic");
				InventoryDetailV2 newInventoryDetail = new InventoryDetailV2(checkoutItem);
				newInventoryDetail.setModifiedOn(today);
				newInventoryDetail.setEditedBy(checkoutUserName);
				newInventoryDetail.setInventoryDetailId(-1); // force creation of new record
				newInventoryDetail.setAmount(0);
				newInventoryDetail.setNumberInUse(requestedCheckoutAmount);
				LocationV2 checkoutLocation = this.locationRepositiory.findByLocationId(checkoutLocationId);
				if (checkoutLocation == null) {
					LOGGER.info("checkout location not found");
					throw new EntityNotFoundException(LocationV2.class, "id", Long.toString(checkoutLocationId));
				}
				newInventoryDetail.setLocation(checkoutLocation);
				if (checkoutSubLocationId != 0) {
					LocationV2 checkoutSubLocation = this.locationRepositiory.findByLocationId(checkoutSubLocationId);
					if (checkoutSubLocation == null) {
						LOGGER.info("checkout sublocation not found");
						throw new EntityNotFoundException(LocationV2.class, "id", Long.toString(checkoutSubLocationId));
					}
					newInventoryDetail.setSubLocation(checkoutSubLocation);
				}
				newInventoryDetail.setCheckoutPurpose(checkoutPurposeReason);
				inventory.addInventoryDetail(newInventoryDetail);
				checkoutItem.setAmount(currentCheckoutAmt - requestedCheckoutAmount);
				checkoutItem.setModifiedOn(today);
				checkoutItem.setEditedBy(checkoutUserName);
				checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
			} else {
				LOGGER.info("Perform 'InUse' plus 'Move' checkout logic");
				checkoutItem.setNumberInUse(currentNumberInUse - requestedCheckoutAmount);
				checkoutItem.setModifiedOn(today);
				checkoutItem.setEditedBy(checkoutUserName);
				checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
			}

		} else if ("Use".equals(checkoutPurpose)) {
			if ("Unopened".equals(checkoutType)) {
				LOGGER.info("Perform 'Unopened' plus 'Use' checkout logic");
				checkoutItem.setAmount(currentCheckoutAmt - requestedCheckoutAmount);
				checkoutItem.setNumberInUse(currentNumberInUse + requestedCheckoutAmount);
				checkoutItem.setModifiedOn(today);
				checkoutItem.setEditedBy(checkoutUserName);
				checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
			} else {
				LOGGER.info("Perform 'InUse' plus 'Use' checkout logic");
				checkoutItem.setNumberInUse(currentNumberInUse - requestedCheckoutAmount);
				checkoutItem.setModifiedOn(today);
				checkoutItem.setEditedBy(checkoutUserName);
				checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
			}

		} else if ("Ship".equals(checkoutPurpose)) {
			LOGGER.info("Perform 'Ship' checkout logic");

			if ("Unopened".equals(checkoutType))
				checkoutItem.setAmount(currentCheckoutAmt - requestedCheckoutAmount);
			else
				checkoutItem.setNumberInUse(currentNumberInUse - requestedCheckoutAmount);
			checkoutItem.setModifiedOn(today);
			checkoutItem.setEditedBy(checkoutUserName);
			checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
		} else if ("Inuse".equals(checkoutType)) {
			LOGGER.info("Perform 'Inuse' checkout logic");
			checkoutItem.setNumberInUse(currentNumberInUse - requestedCheckoutAmount);
			checkoutItem.setModifiedOn(today);
			checkoutItem.setEditedBy(checkoutUserName);
			checkoutItem.setCheckoutPurpose(checkoutPurposeReason);
		}
		inventory.setEditedBy(checkoutUserName);
		inventory.setModifiedOn(today);
		int amountTotal = 0;
		int numberInUse = 0;
		for (InventoryDetailV2 id : inventory.getInventoryDetails()) {
			amountTotal += (id.getAmount() == null ? 0 : id.getAmount());
			numberInUse += (id.getNumberInUse() == null ? 0 : id.getNumberInUse());
		}
		inventory.setAmount(amountTotal);
		inventory.setNumberInUse(numberInUse);
		InventoryV2 savedItem = inventoryRepository.save(inventory);
		LOGGER.info(String.format("Saving User history with details of saved item %s", savedItem));
		String activity;
		if ("Ship".equals(checkoutPurpose)) {
			activity = String.format("%d amount of inventory item has been shipped from %s", requestedCheckoutAmount,
					checkoutType);
		} else {
			activity = "Inventory Detail Checkout";
		}
		super.saveUserHistory(checkoutUserName, "Inventory Detail", activity, checkoutItem.toString());
		return savedItem;
	}

}
