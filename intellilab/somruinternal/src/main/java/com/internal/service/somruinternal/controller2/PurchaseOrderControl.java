package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.AddressV2;
import com.internal.service.somruinternal.model2.CompanyShippingAddressV2;
import com.internal.service.somruinternal.model2.OrderItemV2;
import com.internal.service.somruinternal.model2.PurchaseOrder;
import com.internal.service.somruinternal.repository2.AddressRepository;
import com.internal.service.somruinternal.repository2.CompanyShippingAddressRepsitory;
import com.internal.service.somruinternal.repository2.OrderItemRepositoryV2;
import com.internal.service.somruinternal.repository2.PurchaseOrderRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderControl extends ParentControl {
	@Autowired
	PurchaseOrderRepository purchaseOrderRepo;

	@Autowired
	OrderItemRepositoryV2 orderItemRepo;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	CompanyShippingAddressRepsitory coShippingAddrRepo;

	private final static Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderControl.class);

	@GetMapping("/allPurchaseOrder")
	public List<PurchaseOrder> getPurchaseOrder() {
		LOGGER.info("getAllPurchaseOrders request");
		List<PurchaseOrder> purchaseOrderList = purchaseOrderRepo.findAll();
		LOGGER.info(String.format("Retrieved %d purchaseOrderList rows", purchaseOrderList.size()));
		return purchaseOrderList;
	}

	@PostMapping("/save")
	@Transactional(rollbackFor = Exception.class)
	public PurchaseOrder savePurchaseOrder(@Valid @RequestBody PurchaseOrder purchaseOrder) {
		LOGGER.info(String.format("savePurchaseOrder received request - details %s:", purchaseOrder));

		List<OrderItemV2> orderItemList = purchaseOrder.getOrderItemArray();

		List<OrderItemV2> orderItemListDB = new ArrayList<>();
		for (OrderItemV2 orderItem : orderItemList) {
			OrderItemV2 order = orderItemRepo.findById(orderItem.getOrderItemId()).get();
			orderItemListDB.add(order);
		}

		AddressV2 billingAddress = purchaseOrder.getBillingAddress();
		billingAddress = addressRepository.save(billingAddress);
		purchaseOrder.setBillingAddress(billingAddress);
		CompanyShippingAddressV2 shippingAddress = purchaseOrder.getShippingAddress();
		shippingAddress = coShippingAddrRepo.save(shippingAddress);
		purchaseOrder.setShippingAddress(shippingAddress);

		purchaseOrder.setOrderItemArray(orderItemListDB);
		PurchaseOrder savedPurchaseOrder = purchaseOrderRepo.save(purchaseOrder);

		for (OrderItemV2 orderitem : orderItemListDB) {
			orderitem.setPurchaseOrder(savedPurchaseOrder);
		}
		orderItemRepo.saveAll(orderItemListDB);
		LOGGER.info("Completed saving purchase order");
//		super.saveUserHistory(purchaseOrder.getEditedBy(), "purchaseOrder", "savePurchaseOrder",
//				savedPurchaseOrder.toString());
		return savedPurchaseOrder;
	}

	private PurchaseOrder loadPurchaseOrder(Long purchaseOrderId) {
		PurchaseOrder purchaseOrder = purchaseOrderRepo.findByPurchaseOrderId(purchaseOrderId);
		if (purchaseOrder == null) {
			LOGGER.info("purchaseOrder not found");
			throw new EntityNotFoundException(PurchaseOrder.class, "id", purchaseOrderId.toString());
		}
		Hibernate.initialize(purchaseOrder.getBillingAddress());
		Hibernate.initialize(purchaseOrder.getShippingAddress());
		return purchaseOrder;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<PurchaseOrder> get(@PathVariable(value = "id") Long purchaseOrderId)
			throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with purchaseOrderId %d", purchaseOrderId));
		if (purchaseOrderId == null)
			throw new EntityNotFoundException(PurchaseOrder.class, "id", "");
		PurchaseOrder purchaseOrder = this.loadPurchaseOrder(purchaseOrderId);
		LOGGER.info(String.format("Loaded purchaseOrder details: %s", purchaseOrder));
		return ResponseEntity.ok().body(purchaseOrder);
	}

	@GetMapping("/getPurchaseOrderByOrderItem/{id}")
	public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable(value = "id") Long orderItemId)
			throws EntityNotFoundException {
//		PurchaseOrder purchaseOrder = orderItem.getPurchaseOrder();
		OrderItemV2 orderItem = orderItemRepo.findByOrderItemId(orderItemId);

//		LOGGER.info(String.format("Loaded purchaseOrder details: %s", purchaseOrder));
		return ResponseEntity.ok().body(orderItem.getPurchaseOrder());
	}
}
