package com.internal.service.somruinternal.model2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "il_item_source")
public class ItemSource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dbid;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 50)
	private String type;

	@Column(nullable = false)
	private boolean isActive = false;

	@Column(nullable = false, length = 50)
	private String approvalType;

	@Column(length = 100)
	private String link;

	@Column(nullable = true, length = 50)
	private String editedBy;

	@UpdateTimestamp
	private Date modifiedOn;

	@CreationTimestamp
	private Date createDate;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "billing_address_id")
	private AddressV2 billingAddress;

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public AddressV2 getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressV2 billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	public String diffCompare(ItemSource obj) {
		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("type", this.getType(), obj.getType())
				.append("approvalType", this.getApprovalType(), obj.getApprovalType())
				.append("isActive", this.isActive(), obj.isActive())
				.append("link", this.getLink(), obj.getLink())
				.append("createDate", this.getCreateDate(), obj.getCreateDate());

		return db.build().toString();
	}

}
