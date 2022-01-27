package com.internal.service.somruinternal.model2;

import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@Entity
@Table(name = "il_sales_item")
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class SalesItemV2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long salesItemId;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 50)
	private String catalogNumber;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false)
	private Double unitPrice;

	@Column(nullable = false, length = 50)
	private String editStatus;

	@Column(nullable = true, length = 50)
	private String reviewedBy;

	@Column(nullable = true, length = 5000)
	private String editStatusComment;

	@Column(nullable = true)
	private Date editStatusTime;

	@Column(nullable = true, length = 50)
	private String packSize;

	public SalesItemV2() {
		super();
	}

	/**
	 * @return the salesItemId
	 */
	public long getSalesItemId() {
		return salesItemId;
	}

	/**
	 * @param salesItemId the salesItemId to set
	 */
	public void setSalesItemId(long salesItemId) {
		this.salesItemId = salesItemId;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("salesItemId=");
//        sb.append(salesItemId);
//        sb.append(",name=");
//        sb.append(name);
//        sb.append(",catalogNumber=");
//        sb.append(catalogNumber);
//        sb.append(",active=");
//        sb.append(active);
//        sb.append(",unitPrice=");
//        sb.append(unitPrice);
//        sb.append(",packSize=");
//        sb.append(packSize);
//        sb.append("\n");
//        return sb.toString();
//    }

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
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}

	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the editStatusComment
	 */
	public String getEditStatusComment() {
		return editStatusComment;
	}

	/**
	 * @param editStatusComment the editStatusComment to set
	 */
	public void setEditStatusComment(String editStatusComment) {
		this.editStatusComment = editStatusComment;
	}

	/**
	 * @return the editStatusTime
	 */
	public Date getEditStatusTime() {
		return editStatusTime;
	}

	/**
	 * @param editStatusTime the editStatusTime to set
	 */
	public void setEditStatusTime(Date editStatusTime) {
		this.editStatusTime = editStatusTime;
	}

	/**
	 * @return the packSize
	 */
	public String getPackSize() {
		return packSize;
	}

	/**
	 * @param packSize the packSize to set
	 */
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public DiffResult diff(SalesItemV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("active", this.isActive(), obj.isActive())
				.append("unitPrice", this.getUnitPrice(), obj.getUnitPrice())
				.append("editStatus", this.getEditStatus(), obj.getEditStatus())
				.append("reviewedBy", this.getReviewedBy(), obj.getReviewedBy())
				.append("editStatusComment", this.getEditStatusComment(), obj.getEditStatusComment())
				.append("editStatusTime", this.getEditStatusTime(), obj.getEditStatusTime())
				.append("packSize", this.getPackSize(), obj.getPackSize()).build();

	}

}
