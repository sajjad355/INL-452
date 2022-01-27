package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.OrderItemV2;
import com.internal.service.somruinternal.model2.UserV2;
import com.internal.service.somruinternal.model2.InventoryV2;
import com.internal.service.somruinternal.model2.InventoryDetailV2;
import com.internal.service.somruinternal.repository2.OrderItemRepositoryV2;
import com.internal.service.somruinternal.repository2.UserHistoryRepositoryV2;
import com.internal.service.somruinternal.repository2.InventoryRepositoryV2;
import com.internal.service.somruinternal.utils.SearchUtil;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("/orderitemv2")
public class OrderItemControlV2 extends ParentControl {

	private final static Logger LOGGER = LoggerFactory.getLogger(OrderItemControlV2.class);

	final OrderItemRepositoryV2 orderItemRepo;

	final UserHistoryRepositoryV2 userHistoryRepo;

	final InventoryRepositoryV2 inventoryRepo;

	@Autowired
	public OrderItemControlV2(OrderItemRepositoryV2 orderItemRepo, UserHistoryRepositoryV2 userHistoryRepo,
			InventoryRepositoryV2 inventoryRepo) {
		super();
		this.orderItemRepo = orderItemRepo;
		this.userHistoryRepo = userHistoryRepo;
		this.inventoryRepo = inventoryRepo;
	}

	@GetMapping("/get/{order_item_id}")
	public ResponseEntity<OrderItemV2> getByOrderItemId(@PathVariable(value = "order_item_id") Long orderItemId) {
		LOGGER.info(
				String.format("OrderItemControlV2 getByOrderItemId received request with parameter %d", orderItemId));
		OrderItemV2 orderItem = orderItemRepo.findByOrderItemId(orderItemId);
		if (orderItem == null) {
			LOGGER.info("No OrderItem found");
			return ResponseEntity.notFound().build();
		}
		LOGGER.info("OrderItem found");
		return ResponseEntity.ok().body(orderItem);
	}

	@GetMapping("/loadOrders/{status}/{requestedByUserName}/{urgentOrdersOnly}/{page}")
	public List<OrderItemV2> loadOrders(@PathVariable(value = "status") String status,
			@PathVariable(value = "requestedByUserName") String requestedByUserName,
			@PathVariable(value = "urgentOrdersOnly") boolean urgentOrdersOnly,
			@PathVariable(value = "page") int page) {

		PageRequest pageable = PageRequest.of(page, 10, Sort.by("orderItemId").descending());
		LOGGER.info(String.format("loadOrders receieved request with params %s, %s, %b, %d", status,
				requestedByUserName, urgentOrdersOnly, page));
		List<OrderItemV2> orderList;
		if ("ANY".equals(requestedByUserName) && !urgentOrdersOnly) {
			// orderList = orderItemRepo.loadOrder(status, new PageRequest(page, 10,
			// Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.loadOrder(status, pageable);

		} else if (!("ANY".equals(requestedByUserName)) && !urgentOrdersOnly) {
			// orderList = orderItemRepo.loadOrder(status, requestedByUserName, new
			// PageRequest(page, 10, Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.loadOrder(status, requestedByUserName, pageable);

		} else if ("ANY".equals(requestedByUserName) && urgentOrdersOnly) {
			// orderList = orderItemRepo.loadUrgentOrder(status, new PageRequest(page, 10,
			// Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.loadUrgentOrder(status, pageable);

		} else {
			// orderList = orderItemRepo.loadUrgentOrder(status, requestedByUserName, new
			// PageRequest(page, 10, Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.loadUrgentOrder(status, requestedByUserName, pageable);

		}
		LOGGER.info(String.format("Retrieved %d rows", orderList.size()));
		return orderList;
	}

	@GetMapping("/count/{status}/{requestedByUserName}/{urgentOrdersOnly}")
	public Long getCount(@PathVariable(value = "status") String status,
			@PathVariable(value = "requestedByUserName") String requestedByUserName,
			@PathVariable(value = "urgentOrdersOnly") boolean urgentOrdersOnly) {
		LOGGER.info(String.format("getCount receieved request with params %s, %s, %b", status, requestedByUserName,
				urgentOrdersOnly));
		Long orderCount;
		if ("ANY".equals(requestedByUserName) && !urgentOrdersOnly) {
			orderCount = orderItemRepo.countOrder(status);
		} else if (!("ANY".equals(requestedByUserName)) && !urgentOrdersOnly) {
			orderCount = orderItemRepo.countOrder(status, requestedByUserName);
		} else if ("ANY".equals(requestedByUserName) && urgentOrdersOnly) {
			orderCount = orderItemRepo.countUrgentOrders(status);
		} else {
			orderCount = orderItemRepo.countUrgentOrders(status, requestedByUserName);
		}
		LOGGER.info(String.format("Retrieved %d rows", orderCount));
		return orderCount;

	}

	@GetMapping("/search/{status}/{searchKey}/{requestedByUserName}/{urgentOrdersOnly}/{page}")
	public List<OrderItemV2> searchOrderItem(@PathVariable(value = "status") String status,
			@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "requestedByUserName") String requestedByUserName,
			@PathVariable(value = "urgentOrdersOnly") boolean urgentOrdersOnly,
			@PathVariable(value = "page") int page) {
		PageRequest pageable = PageRequest.of(page, 10, Sort.by("orderItemId").descending());
		LOGGER.info(String.format("searchOrderItem receieved request with params %s, %s, %s, %b, %d", status, searchKey,
				requestedByUserName, urgentOrdersOnly, page));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		List<OrderItemV2> orderList;
		if ("ANY".equals(requestedByUserName) && !urgentOrdersOnly) {
			// orderList = orderItemRepo.searchOrder(status, search, new PageRequest(page,
			// 10, Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.searchOrder(status, search, pageable);

		} else if (!("ANY".equals(requestedByUserName)) && !urgentOrdersOnly) {
			// orderList = orderItemRepo.searchOrder(status, search, requestedByUserName,
			// new PageRequest(page, 10, Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.searchOrdersByRequestedUser(status, search, requestedByUserName, pageable);

		} else if ("ANY".equals(requestedByUserName) && urgentOrdersOnly) {
			// orderList = orderItemRepo.searchUrgentOrder(status, search, new
			// PageRequest(page, 10, Sort.Direction.DESC, "orderItemId"));
			orderList = orderItemRepo.searchUrgentOrder(status, search, pageable);
		} else {
			// orderList = orderItemRepo.searchUrgentOrder(status, search,
			// requestedByUserName, new PageRequest(page, 10, Sort.Direction.DESC,
			// "orderItemId"));
			orderList = orderItemRepo.searchUrgentOrderByRequestedUser(status, search, requestedByUserName, pageable);
		}
		LOGGER.info(String.format("Order Search returned %d results", orderList.size()));
		return orderList;
	}

	@GetMapping("/searchCount/{status}/{searchKey}/{requestedByUserName}/{urgentOrdersOnly}")
	public Long searchOrderItemCount(@PathVariable(value = "status") String status,
			@PathVariable(value = "searchKey") String searchKey,
			@PathVariable(value = "requestedByUserName") String requestedByUserName,
			@PathVariable(value = "urgentOrdersOnly") boolean urgentOrdersOnly) {
		LOGGER.info(String.format("searchOrderItemCount receieved request with params %s, %s, %s, %b", status,
				searchKey, requestedByUserName, urgentOrdersOnly));
		String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
		Long orderCount;
		if ("ANY".equals(requestedByUserName) && !urgentOrdersOnly) {
			orderCount = orderItemRepo.searchOrderCount(status, search);
		} else if (!("ANY".equals(requestedByUserName)) && !urgentOrdersOnly) {
			orderCount = orderItemRepo.searchOrderCountByRequestedUser(status, search, requestedByUserName);
		} else if ("ANY".equals(requestedByUserName) && urgentOrdersOnly) {
			orderCount = orderItemRepo.searchUrgentOrderCount(status, search);
		} else {
			orderCount = orderItemRepo.searchUrgentOrderCountByRequestedUser(status, search, requestedByUserName);
		}
		LOGGER.info(String.format("Search count returned %d results", orderCount));
		return orderCount;
	}

	private OrderItemV2 loadOrderItem(Long orderItemId) {
		OrderItemV2 orderItem = orderItemRepo.findByOrderItemId(orderItemId);
		if (orderItem == null) {
			LOGGER.info("OrderItem not found");
			throw new EntityNotFoundException(OrderItemV2.class, "id", orderItemId.toString());
		}
		Hibernate.initialize(orderItem.getRequestUser());
		Hibernate.initialize(orderItem.getApproveUser());
		Hibernate.initialize(orderItem.getOrderUser());
		Hibernate.initialize(orderItem.getReceiveUser());
		Hibernate.initialize(orderItem.getRejectUser());
		Hibernate.initialize(orderItem.getReserveForClient());
		return orderItem;
	}

	@PostMapping("/save")
	public List<OrderItemV2> saveOrderItem(
		@RequestBody
		@NotEmpty(message = "Order item list cannot be empty.")
			List<@Valid OrderItemV2 > orderItemList
	) {
		List<OrderItemV2> savedOrderItems = orderItemRepo.saveAll(orderItemList);
		super.saveUserHistory(savedOrderItems.get(0).getEditedBy(), "OrderItem", "saveOrderItem", "Total Order Itm Saved/Updated:" + savedOrderItems.size());

		LOGGER.info("Successfully created/updated order item and UserHistory");
		return savedOrderItems;
	}

	private String getDiffs(OrderItemV2 lhs, OrderItemV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two OrderItemV2 objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("OrderItemV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@PostMapping("/receive")
	public InventoryV2 receiveOrderItem(@Valid @RequestBody UserV2 receiveUser, @RequestParam long orderItemId,
			@RequestParam int receivedAmount) {
		InventoryV2 receivedInventory;
		InventoryDetailV2 receivedDetailItem;
		LOGGER.info("receiveOrderItem called with following params");
		LOGGER.info(String.format("id: %d", orderItemId));
		LOGGER.info(String.format("user: %s", receiveUser));
		LOGGER.info(String.format("receivedAmount: %d", receivedAmount));

		OrderItemV2 orderItem = orderItemRepo.findByOrderItemId(orderItemId);
		if (orderItem == null) {
			LOGGER.info("No OrderItem found");
			throw new EntityNotFoundException(OrderItemV2.class, "id", Long.toString(orderItemId));
		}
		LOGGER.info(String.format("Loaded following OrderItem %s", orderItem));
		Date today = new Date();
		orderItem.setEditedBy(receiveUser.getName());
		orderItem.setModifiedOn(today);
		orderItem.setReceiveTime(today);
		orderItem.setReceiveUser(receiveUser);

		if (receivedAmount >= orderItem.getAmount()) {
			orderItem.setStatus("Received");
			orderItem.setBackOrderedAmount(0D);
		} else if (orderItem.getBackOrderedAmount() != null && orderItem.getBackOrderedAmount() > 0
				&& receivedAmount >= orderItem.getBackOrderedAmount()) {
			orderItem.setStatus("Received");
			orderItem.setBackOrderedAmount(0D);
		} else {
			if (orderItem.getBackOrderedAmount() == null || orderItem.getBackOrderedAmount() == 0)
				orderItem.setBackOrderedAmount(orderItem.getAmount() - receivedAmount);
			else
				orderItem.setBackOrderedAmount(orderItem.getBackOrderedAmount() - receivedAmount);

			String comment = String.format("Order was partially received - %.2f still to be recevied as of %s",
					orderItem.getBackOrderedAmount(), orderItem.getReceiveTime());

			if (orderItem.getComment() != null)
				orderItem.setComment(orderItem.getComment() + "\n" + comment);
			else
				orderItem.setComment(comment);
		}
		LOGGER.info(String.format("about to save received order: %s", orderItem));
		OrderItemV2 savedOrderItem = orderItemRepo.save(orderItem);
		super.saveUserHistory(savedOrderItem.getEditedBy(), "OrderItem", "receiveOrder", savedOrderItem.toString());
		LOGGER.info("Successfully received order item and UserHistory - updating Inventory now");
		List<InventoryV2> items = inventoryRepo.searchByNameAndCatalogNumberExactMatch(orderItem.getName(),
				orderItem.getCatalogNumber());
		LOGGER.info(String.format("Returned %d Inventory rows", items.size()));
		if (items.isEmpty()) {
			LOGGER.info("No matching inventory item based on name and catalog number - create one");
			receivedInventory = this.createInventoryItemFromReceivedOrderItem(orderItem, receivedAmount);
			receivedDetailItem = this.createInventoryDetailItemFromReceivedOrderItem(orderItem, receivedAmount);
			receivedInventory.addInventoryDetail(receivedDetailItem);
		} else {
			// simply take the first one - there should only be max 1 record based on name +
			// catalog # search
			receivedInventory = items.get(0);
			LOGGER.info(String.format("Found matching inventory item based on name and catalog number - details: %s",
					receivedInventory));
			receivedDetailItem = this.createInventoryDetailItemFromReceivedOrderItem(orderItem, receivedAmount);
			receivedInventory.addInventoryDetail(receivedDetailItem);
			int totalAmt = 0;
			List<InventoryDetailV2> details = receivedInventory.getInventoryDetails();
			for (InventoryDetailV2 detail : details) {
				totalAmt += detail.getAmount();
			}
			receivedInventory.setAmount(totalAmt);
		}
		LOGGER.info(String.format("Saving Inventory Item: %s", receivedInventory));
		InventoryV2 savedItem = inventoryRepo.save(receivedInventory);
		LOGGER.info(String.format("Saving User history with details of saved item %s", savedItem));
		super.saveUserHistory(savedItem.getEditedBy(), "Inventory", "Receiving Order created new Inventory item",
				savedItem.toString());
		return savedItem;
	}

	private InventoryV2 createInventoryItemFromReceivedOrderItem(OrderItemV2 orderItem, int receivedAmount) {
		InventoryV2 inventoryItem = new InventoryV2();
		inventoryItem.setEditedBy(orderItem.getEditedBy());
		inventoryItem.setModifiedOn(orderItem.getModifiedOn());
		inventoryItem.setName(orderItem.getName());
		inventoryItem.setActive(true);
		inventoryItem.setCategory(orderItem.getCategory());
		inventoryItem.setCatalogNumber(orderItem.getCatalogNumber());
		inventoryItem.setType(orderItem.getType());
		inventoryItem.setSubtype(orderItem.getSubtype());
		inventoryItem.setUnit(orderItem.getUnit());
		inventoryItem.setContainerSize(orderItem.getContainerSize());
		inventoryItem.setSupplier(orderItem.getSupplier());
		inventoryItem.setManufacturer(orderItem.getManufacturer());
		inventoryItem.setUnitPrice(orderItem.getUnitPrice());
		inventoryItem.setAmount(receivedAmount);
		inventoryItem.setNumberInUse(0);
		inventoryItem.setQuantityThreshold(0);
		return inventoryItem;
	}

	private InventoryDetailV2 createInventoryDetailItemFromReceivedOrderItem(OrderItemV2 orderItem,
			int receivedAmount) {
		InventoryDetailV2 inventoryDetailItem = new InventoryDetailV2();
		inventoryDetailItem.setEditedBy(orderItem.getEditedBy());
		inventoryDetailItem.setModifiedOn(orderItem.getModifiedOn());
		inventoryDetailItem.setName(orderItem.getName());
		inventoryDetailItem.setActive(true);
		inventoryDetailItem.setMeetsAcceptanceCriteria(false);
		inventoryDetailItem.setAmount(receivedAmount);
		// assign temporary lot number as GUID
		inventoryDetailItem.setLotNumber(java.util.UUID.randomUUID().toString());
		inventoryDetailItem.setReceivedDate(orderItem.getReceiveTime());
		inventoryDetailItem.setProjectNumber(orderItem.getProject());
		inventoryDetailItem.setReceiveUser(orderItem.getReceiveUser());
		if (orderItem.getReserveForClient() != null) {
			inventoryDetailItem.setReserve(true);
			inventoryDetailItem.setReserveForClient(orderItem.getReserveForClient());
		} else {
			inventoryDetailItem.setReserve(false);
		}
		inventoryDetailItem.setUnit(orderItem.getUnit());
		inventoryDetailItem.setContainerSize(orderItem.getContainerSize());
		inventoryDetailItem.setReconstituted(false);
		return inventoryDetailItem;
	}
}
