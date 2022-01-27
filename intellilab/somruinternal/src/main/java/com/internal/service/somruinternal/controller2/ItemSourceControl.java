package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.utils.SearchUtil;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;

@RestController
@RequestMapping("/itemSource")

public class ItemSourceControl extends ParentControl {

	private final static Logger LOGGER = LoggerFactory.getLogger(ItemSourceControl.class);

	@Autowired
	ItemSourceRepository itemSourceRepo;

	@Autowired
	AddressRepository addressRepository;

	@GetMapping("/getAllItemSource")
	public List<ItemSource> allItemSource() {
		LOGGER.info("getAllItemSource received request");
		List<ItemSource> itemSourcelist = itemSourceRepo.findAll();
		LOGGER.info(String.format("Retrieved %d itemSource", itemSourcelist.size()));
		return itemSourcelist;
	}

	@GetMapping("/getItemSourcesBy")
	public List<ItemSource> getItemSourceBy(@RequestParam(required = false) String type,
			@RequestParam(required = false) Boolean status) {
		LOGGER.info("getItemSourcesBy received request");
		List<ItemSource> itemSourcelist = new ArrayList<>();
		if (type != null && status != null) {
			itemSourcelist = itemSourceRepo.findByTypeAndIsActive(type, status);
		} else if (type != null) {
			itemSourcelist = itemSourceRepo.findByType(type);
		} else if (status != null) {
			itemSourcelist = itemSourceRepo.findByIsActive(status);
		} else {
			itemSourcelist = itemSourceRepo.findAll();
		}

		LOGGER.info(String.format("Retrieved %d itemSource", itemSourcelist.size()));

		return itemSourcelist;
	}

	private ItemSource loadItemSource(Long dbId) {
		ItemSource itemsource = itemSourceRepo.findByDbid(dbId);
		if (itemsource == null) {
			LOGGER.info("itemsource not found");
			throw new EntityNotFoundException(ItemSource.class, "id", dbId.toString());
		}
		return itemsource;
	}

	@GetMapping("/getItemSourcesByNameandType")
	public List<ItemSource> getItemSourceByNameAndType(@RequestParam(required = false) String type,
			@RequestParam(required = false) String name) {
		LOGGER.info("getItemSourcesBy received request");
		List<ItemSource> itemSourcelist = new ArrayList<>();
		itemSourcelist = itemSourceRepo.findByTypeAndName(name, type);

		LOGGER.info(String.format("Retrieved %d itemSource", itemSourcelist.size()));

		return itemSourcelist;
	}

	@PostMapping("/addItemSource")
	public ItemSource saveAccount(@RequestBody ItemSource itemsource) {

		LOGGER.info(String.format("addItemSource received request - details %s:", itemsource));

		String action;
		String auditTrailRecord;
		if (itemsource.getDbid() > 0) {
			ItemSource previousitemsourceDetails = this.loadItemSource(itemsource.getDbid());
			action = "update itemsource";
			auditTrailRecord = getDiffs(itemsource, previousitemsourceDetails);
                        LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		} else {
			action = "insert itemsource";
			auditTrailRecord = itemsource.toString();
                        LOGGER.info(String.format("%s %s:", action, auditTrailRecord));
		}
		AddressV2 billingAddress = itemsource.getBillingAddress();
		billingAddress = addressRepository.save(billingAddress);
		itemsource.setBillingAddress(billingAddress);

		ItemSource savedItemSource = itemSourceRepo.save(itemsource);
		super.saveUserHistory(itemsource.getEditedBy(), "Item Source", action, auditTrailRecord);
		LOGGER.info("Completed saving Item Source");
		return savedItemSource;
	}

	private String getDiffs(ItemSource lhs, ItemSource rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two Item Source objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("ItemSource GET DIFFS RESULT: %s", diffs));
		return diffs;
	}

	@GetMapping("/searchItemSource/{key}")
	public List<ItemSource> searchItemSourecByNameOrType(@PathVariable(value = "key") String key) {
		LOGGER.info(String.format(
				"/ItemSource received GET /search/{key} mapped to searchItemSourecByNameOrTypeOrStatus with key %s",
				key));
		return itemSourceRepo.searchItemSourecByNameOrType(SearchUtil.replaceIllegalSearchCharacters(key));
	}
}
