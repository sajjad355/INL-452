package com.internal.service.somruinternal.model2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "il_purchase_order")
public class PurchaseOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long purchaseOrderId;

	@Column(nullable = true, length = 100)
	private String supplier;

	@Column(nullable = true, length = 100)
	private String salesPerson;

	@Column(nullable = true, length = 100)
	private String quoteNumber;

	@Column(nullable = true, length = 100)
	private String requisitioner;

	@Column(nullable = true, length = 100)
	private String shipVia;

	@Column(nullable = true, length = 100)
	private String paymentTerms;

	@Column(nullable = true, length = 100)
	private String shippingTerms;

	@Column(nullable = true, length = 50)
	private String editedBy;

	@UpdateTimestamp
	private Date modifiedOn;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "billing_address_id")
	private AddressV2 billingAddress;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "shipping_address_id")
	private CompanyShippingAddressV2 shippingAddress;

	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.PERSIST)
	private List<OrderItemV2> orderItemArray = new ArrayList<OrderItemV2>();

	public PurchaseOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseOrder(long purchaseOrderId, String supplier, String salesPerson, String quoteNumber,
			String requisitioner, String shipVia, String paymentTerms, String shippingTerms, String editedBy,
			Date modifiedOn, AddressV2 billingAddress, CompanyShippingAddressV2 shippingAddress,
			List<OrderItemV2> orderItemArray) {
		super();
		this.purchaseOrderId = purchaseOrderId;
		this.supplier = supplier;
		this.salesPerson = salesPerson;
		this.quoteNumber = quoteNumber;
		this.requisitioner = requisitioner;
		this.shipVia = shipVia;
		this.paymentTerms = paymentTerms;
		this.shippingTerms = shippingTerms;
		this.editedBy = editedBy;
		this.modifiedOn = modifiedOn;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.orderItemArray = orderItemArray;
	}

	public long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getRequisitioner() {
		return requisitioner;
	}

	public void setRequisitioner(String requisitioner) {
		this.requisitioner = requisitioner;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getShippingTerms() {
		return shippingTerms;
	}

	public void setShippingTerms(String shippingTerms) {
		this.shippingTerms = shippingTerms;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public AddressV2 getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressV2 billingAddress) {
		this.billingAddress = billingAddress;
	}

	public CompanyShippingAddressV2 getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(CompanyShippingAddressV2 shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<OrderItemV2> getOrderItemArray() {
		return orderItemArray;
	}

	public void setOrderItemArray(List<OrderItemV2> orderItemArray) {
		this.orderItemArray = orderItemArray;
	}

}
