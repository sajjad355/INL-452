package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "il_sop_component")
public class SopComponent implements Diffable<SopComponent> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long componentId;

	@Column(nullable = true, length = 100)
	private String itemName;

	@Column(nullable = true)
	private double requiredQuantity;

	@Column(nullable = true, length = 50)
	private String requiredUnit;

	@Column(nullable = true, length = 100)
	private String vendor;

	@Column(nullable = true, length = 50)
	private String catalogNumber;

	@Column(nullable = true, length = 50)
	private String supplierCatalogNumber;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sop_id")
	@JsonBackReference
	private SopV2 sop;

	public SopComponent() {
		super();
	}

	/**
	 * @return the ingredientId
	 */
	public long getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId the componentId to set
	 */
	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the requiredQuantity
	 */
	public double getRequiredQuantity() {
		return requiredQuantity;
	}

	/**
	 * @param requiredQuantity the requiredQuantity to set
	 */
	public void setRequiredQuantity(double requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	/**
	 * @return the requiredUnit
	 */
	public String getRequiredUnit() {
		return requiredUnit;
	}

	/**
	 * @param requiredUnit the requiredUnit to set
	 */
	public void setRequiredUnit(String requiredUnit) {
		this.requiredUnit = requiredUnit;
	}

	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the category
	 */
	public String getCatalogNumber() {
		return catalogNumber;
	}

	/**
	 * @param category the category to set
	 */
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	/**
	 * @return the supplierCategory
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

	/**
	 * @return the sop
	 */
	public SopV2 getSop() {
		return sop;
	}

	/**
	 * @param sop the sop to set
	 */
	public void setSop(SopV2 sop) {
		this.sop = sop;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "componentId=");
//        sb.append( componentId );
//        sb.append( ",itemName=");
//        sb.append( itemName );
//        sb.append( ",requiredQuantity=");
//        sb.append( requiredQuantity );
//        sb.append( ",requiredUnit=");
//        sb.append( requiredUnit );
//        sb.append( ",vendor=");
//        sb.append( vendor );
//        sb.append( ",catalogNumber=");
//        sb.append( catalogNumber );
//        sb.append( ",supplierCatalogNumber=");
//        sb.append( supplierCatalogNumber );
//        sb.append( ",editedBy=");
//        sb.append( editedBy );
//        sb.append( ",modifiedOn=");
//        sb.append( modifiedOn );
//        
//        return sb.toString();
//    }

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(SopComponent obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("itemName", this.getItemName(), obj.getItemName())
				.append("requiredQuantity", this.getRequiredQuantity(), obj.getRequiredQuantity())
				.append("requiredUnit", this.getRequiredUnit(), obj.getRequiredUnit())
				.append("vendor", this.getVendor(), obj.getVendor())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("supplierCatalogNumber", this.getSupplierCatalogNumber(), obj.getSupplierCatalogNumber())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("sop", this.getSop(), obj.getSop()).build();

	}

}
