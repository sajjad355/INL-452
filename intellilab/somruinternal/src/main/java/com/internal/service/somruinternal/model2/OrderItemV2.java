package com.internal.service.somruinternal.model2;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

@Entity
@Table(name = "il_order_item")
public class OrderItemV2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderItemId;

	@Column(nullable = true, length = 50)
	private String catalogNumber;

	@Column(nullable = true, length = 50)
	private String category;

	@Column(nullable = true, length = 50)
	private String supplierCatalogNumber;

	@Column(nullable = false, length = 200)
	private String name;

	@Column(nullable = true, length = 50)
	private String type;

	@Column(nullable = true, length = 50)
	private String subtype;

	@Column(nullable = true, length = 100)
	private String supplier;

	@Column(nullable = true, length = 100)
	private String manufacturer;

	@Column(nullable = true)
	private Double amount;

	@Column(nullable = true)
	private Double backOrderedAmount;

	@Column(nullable = true, length = 50)
	private String unit;

	@Column(nullable = true)
	private String currency;

	@Column(nullable = true)
	private Double currencyRate;

	@Column(nullable = true)
	private Double containerSize;

	@Column(nullable = true)
	private Double unitPrice;

	// was called grantid in old model
	@Column(nullable = true, length = 100)
	private String project;

	@ManyToOne(optional = true)
	@JoinColumn(name = "request_user_id")
	private UserV2 requestUser;

	@ManyToOne(optional = true)
	@JoinColumn(name = "approve_user_id")
	private UserV2 approveUser;

	@ManyToOne(optional = true)
	@JoinColumn(name = "order_user_id")
	private UserV2 orderUser;

	@ManyToOne(optional = true)
	@JoinColumn(name = "recieve_user_id")
	private UserV2 receiveUser;

	@ManyToOne(optional = true)
	@JoinColumn(name = "reject_user_id")
	private UserV2 rejectUser;

	@Column(nullable = false, length = 50)
	private String status;

	@Column(nullable = true, length = 2000)
	private String statusReason;

	@Column(nullable = true)
	private Date requestTime;

	@Column(nullable = true)
	private Date approveTime;

	@Column(nullable = true)
	private Date orderTime;

	@Column(nullable = true)
	private Date receiveTime;

	@Column(nullable = true)
	private Date rejectedTime;

	@Column(nullable = true)
	private Date eta;

	@Column(nullable = true)
	private boolean urgent;

	@Column(nullable = true, length = 5000)
	private String comment;

	@ManyToOne(optional = true)
	@JoinColumn(name = "reserve_client_id")
	private ClientCompanyV2 reserveForClient;

	@NotBlank
	@Column(nullable = false)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@ManyToOne
	@JoinColumn(name = "fk_purchase_order_id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "purchaseOrderId")
	private PurchaseOrder purchaseOrder;

	public OrderItemV2() {
		super();
	}

	/**
	 * @return the orderItemId
	 */
	public long getOrderItemId() {
		return orderItemId;
	}

	/**
	 * @param orderItemId the orderItemId to set
	 */
	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
	 * @return the catalogNumber
	 */
	public String getCatalogNumber() {
		return catalogNumber;
	}

	/**
	 * @param catalogNumber the catalogNumber to set
	 */
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the supplierCatalogNumber
	 */
	public String getSupplierCatalogNumber() {
		return supplierCatalogNumber;
	}

	/**
	 * @param supplierCatalogNumber the supplierCatalogNumber to set
	 */
	public void setSupplierCatalogNumber(String supplierCatalogNumber) {
		this.supplierCatalogNumber = supplierCatalogNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the supplier
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the backOrderedAmount
	 */
	public Double getBackOrderedAmount() {
		return backOrderedAmount;
	}

	/**
	 * @param backOrderedAmount the backOrderedAmount to set
	 */
	public void setBackOrderedAmount(Double backOrderedAmount) {
		this.backOrderedAmount = backOrderedAmount;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the unitPrice
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @return the approveTime
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * @param approveTime the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * @return the orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the receiveTime
	 */
	public Date getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime the receiveTime to set
	 */
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return the eta
	 */
	public Date getEta() {
		return eta;
	}

	/**
	 * @param eta the eta to set
	 */
	public void setEta(Date eta) {
		this.eta = eta;
	}

	/**
	 * @return the urgent
	 */
	public boolean isUrgent() {
		return urgent;
	}

	/**
	˜ * @param urgent the urgent to set
	 */
	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the reserveForClient
	 */
	public ClientCompanyV2 getReserveForClient() {
		return reserveForClient;
	}

	/**
	 * @param reserveForClient the reserveForClient to set
	 */
	public void setReserveForClient(ClientCompanyV2 reserveForClient) {
		this.reserveForClient = reserveForClient;
	}

	/**
	 * @return the editedBy
	 */
	public String getEditedBy() {
		return editedBy;
	}

	/**
	 * @param editedBy the editedBy to set
	 */
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("orderItemId=");
//        sb.append(orderItemId);
//        sb.append(",catalogNumber=");
//        sb.append(catalogNumber);
//        sb.append(",category=");
//        sb.append(category);
//        sb.append(",supplierCatalogNumber=");
//        sb.append(supplierCatalogNumber);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        sb.append(",editedBy=");
//        sb.append(editedBy);
//        sb.append(",comment=");
//        sb.append(comment);
//        sb.append(",urgent=");
//        sb.append(urgent);
//        sb.append(",eta=");
//        sb.append(eta);
//        sb.append(",receiveTime=");
//        sb.append(receiveTime);
//        sb.append(",orderTime=");
//        sb.append(orderTime);
//        sb.append(",approveTime=");
//        sb.append(approveTime);
//        sb.append(",requestTime=");
//        sb.append(requestTime);
//        sb.append(",rejectedTime=");
//        sb.append(rejectedTime);
//        sb.append(",orderUser=");
//        sb.append(orderUser);
//        sb.append(",approveUser=");
//        sb.append(approveUser);
//        sb.append(",status=");
//        sb.append(status);
//        sb.append(",statusReason=");
//        sb.append(statusReason);
//        sb.append(",receiveUser=");
//        sb.append(receiveUser);
//        sb.append(",requestPerson=");
//        sb.append(requestUser);
//        sb.append(",project=");
//        sb.append(project);
//        sb.append(",unitPrice=");
//        sb.append(unitPrice);
//        sb.append(",containerSize=");
//        sb.append(containerSize);
//        sb.append(",unit=");
//        sb.append(unit);
//        sb.append(",backOrderedAmount=");
//        sb.append(backOrderedAmount);
//        sb.append(",amount=");
//        sb.append(amount);
//        sb.append(",manufacturer=");
//        sb.append(manufacturer);
//        sb.append(",name=");
//        sb.append(name);
//        sb.append(",type=");
//        sb.append(type);
//        sb.append(",subtype=");
//        sb.append(subtype);
//        sb.append(",supplier=");
//        sb.append(supplier);
//        sb.append("\n");
//        return sb.toString();
//    }

	/**
	 * @return the requestUser
	 */
	public UserV2 getRequestUser() {
		return requestUser;
	}

	/**
	 * @param requestUser the requestUser to set
	 */
	public void setRequestUser(UserV2 requestUser) {
		this.requestUser = requestUser;
	}

	/**
	 * @return the approveUser
	 */
	public UserV2 getApproveUser() {
		return approveUser;
	}

	/**
	 * @param approveUser the approveUser to set
	 */
	public void setApproveUser(UserV2 approveUser) {
		this.approveUser = approveUser;
	}

	/**
	 * @return the orderUser
	 */
	public UserV2 getOrderUser() {
		return orderUser;
	}

	/**
	 * @param orderUser the orderUser to set
	 */
	public void setOrderUser(UserV2 orderUser) {
		this.orderUser = orderUser;
	}

	/**
	 * @return the receiveUser
	 */
	public UserV2 getReceiveUser() {
		return receiveUser;
	}

	/**
	 * @param receiveUser the receiveUser to set
	 */
	public void setReceiveUser(UserV2 receiveUser) {
		this.receiveUser = receiveUser;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	/**
	 * @return the containerSize
	 */
	public Double getContainerSize() {
		return containerSize;
	}

	/**
	 * @param containerSize the containerSize to set
	 */
	public void setContainerSize(Double containerSize) {
		this.containerSize = containerSize;
	}

	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the rejectUser
	 */
	public UserV2 getRejectUser() {
		return rejectUser;
	}

	/**
	 * @param rejectUser the rejectUser to set
	 */
	public void setRejectUser(UserV2 rejectUser) {
		this.rejectUser = rejectUser;
	}

	/**
	 * @return the rejectedTime
	 */
	public Date getRejectedTime() {
		return rejectedTime;
	}

	/**
	 * @param rejectedTime the rejectedTime to set
	 */
	public void setRejectedTime(Date rejectedTime) {
		this.rejectedTime = rejectedTime;
	}

	/**
	 * @return the subtype
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * @param subtype the subtype to set
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(OrderItemV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("category", this.getCategory(), obj.getCategory())
				.append("supplierCatalogNumber", this.getSupplierCatalogNumber(), obj.getSupplierCatalogNumber())
				.append("name", this.getName(), obj.getName()).append("type", this.getType(), obj.getType())
				.append("subtype", this.getSubtype(), obj.getSubtype())
				.append("supplier", this.getSupplier(), obj.getSupplier())
				.append("manufacturer", this.getManufacturer(), obj.getManufacturer())
				.append("amount", this.getAmount(), obj.getAmount())
				.append("backOrderedAmount", this.getBackOrderedAmount(), obj.getBackOrderedAmount())
				.append("unit", this.getUnit(), obj.getUnit())
				.append("containerSize", this.getContainerSize(), obj.getContainerSize())
				.append("unitPrice", this.getUnitPrice(), obj.getUnitPrice())
				.append("project", this.getProject(), obj.getProject())
				.append("status", this.getStatus(), obj.getStatus())
				.append("statusReason", this.getStatusReason(), obj.getStatusReason())
				.append("requestTime", this.getRequestTime(), obj.getRequestTime())
				.append("approveTime", this.getApproveTime(), obj.getApproveTime())
				.append("orderTime", this.getOrderTime(), obj.getOrderTime())
				.append("receiveTime", this.getReceiveTime(), obj.getReceiveTime())
				.append("rejectedTime", this.getRejectedTime(), obj.getRejectedTime())
				.append("eta", this.getEta(), obj.getEta()).append("urgent", this.isUrgent(), obj.isUrgent())
				.append("comment", this.getComment(), obj.getComment())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		if (this.reserveForClient != null) {
			DiffResult reserveForClientDiffs = this.reserveForClient.diff(obj.getReserveForClient());
			db.append("reserveForClient", reserveForClientDiffs);
		}

		diffStringBuilder.append(db.build().toString());

		return diffStringBuilder.toString();
	}

}
