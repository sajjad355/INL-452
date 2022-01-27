package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "il_inventory")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryV2 {

	// Non-nullable fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long inventoryId;

	@Column(nullable = false, length = 50)
	private String editedBy, type;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer numberInUse;

	// Nullable fields
	@Column(nullable = true, length = 50)
	private String category;

	@Column(nullable = true, length = 50)
	private String catalogNumber;

	@Column(nullable = true, length = 100)
	private String manufacturer;

	@Column(nullable = true, length = 50)
	private String supplierCatalogNumber;

	@Column(nullable = true, length = 100)
	private String supplier;

	@Column(nullable = true, length = 50)
	private String subtype;

	@Column(nullable = true, length = 50)
	private String unit;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer quantityThreshold;

	@Column(nullable = true, length = 255)
	private String supplierLink;

	@Column(nullable = true, length = 255)
	private String manufacturerLink;

	@Column(nullable = true, length = 50)
	private String chemicalAbstractsServiceNumber;

	@Column(nullable = true, length = 100)
	private String compoundName;

	@Column(nullable = true, length = 100)
	private String tradeName;

	@Column(nullable = true, length = 50)
	private String drugType;

	@Column(nullable = true, length = 50)
	private String denominationUnit;

	@Column(nullable = true, length = 50)
	private String molarChallengeRatio;

	@Column(nullable = true, length = 50)
	private String conjugationType;

	@Column(nullable = true, length = 50)
	private String clonality;

	@Column(nullable = true, length = 50)
	private String host;

	@Column()
	private Double unitPrice, containerSize, molecularWeight;

	@Column(nullable = false)
	private boolean active;

	@Column()
	private Integer packDenomination, checkoutDenomination;

	@Column(length = 5000)
	private String comment;

	@OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InventoryDetailV2> inventoryDetails = new ArrayList<>();

	@Column()
	private Long oldInventoryId;

	public InventoryV2() {
		super();
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
	 * @return the subType
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubtype(String subType) {
		this.subtype = subType;
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
	 * @return the containerSize
	 */
	public Double getContainerSize() {
		return containerSize;
	}

	/**
	 * @param containerSize the containerSize to set
	 */
	public void setContainerSize(double containerSize) {
		this.setContainerSize((Double) containerSize);
	}

	/**
	 * @return the quantityThreshold
	 */
	public Integer getQuantityThreshold() {
		return quantityThreshold;
	}

	/**
	 * @param quantityThreshold the quantityThreshold to set
	 */
	public void setQuantityThreshold(Integer quantityThreshold) {
		this.quantityThreshold = quantityThreshold;
	}

	/**
	 * @return the supplierLink
	 */
	public String getSupplierLink() {
		return supplierLink;
	}

	/**
	 * @param supplierLink the supplierLink to set
	 */
	public void setSupplierLink(String supplierLink) {
		this.supplierLink = supplierLink;
	}

	/**
	 * @return the manufacturerLink
	 */
	public String getManufacturerLink() {
		return manufacturerLink;
	}

	/**
	 * @param manufacturerLink the manufacturerLink to set
	 */
	public void setManufacturerLink(String manufacturerLink) {
		this.manufacturerLink = manufacturerLink;
	}

	/**
	 * @return the chemicalAbstractServiceNumber
	 */
	public String getChemicalAbstractsServiceNumber() {
		return chemicalAbstractsServiceNumber;
	}

	/**
	 * @param chemicalAbstractServiceNumber the chemicalAbstractServiceNumber to set
	 */
	public void setChemicalAbstractsServiceNumber(String chemicalAbstractServiceNumber) {
		this.chemicalAbstractsServiceNumber = chemicalAbstractServiceNumber;
	}

	/**
	 * @return the molecularWeight
	 */
	public Double getMolecularWeight() {
		return molecularWeight;
	}

	/**
	 * @param molecularWeight the molecularWeight to set
	 */
	public void setMolecularWeight(Double molecularWeight) {
		this.molecularWeight = molecularWeight;
	}

	/**
	 * @return the compoundName
	 */
	public String getCompoundName() {
		return compoundName;
	}

	/**
	 * @param compoundName the compoundName to set
	 */
	public void setCompoundName(String compoundName) {
		this.compoundName = compoundName;
	}

	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * @return the drugType
	 */
	public String getDrugType() {
		return drugType;
	}

	/**
	 * @param drugType the drugType to set
	 */
	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}

	/**
	 * @return the packDenomination
	 */
	public Integer getPackDenomination() {
		return packDenomination;
	}

	/**
	 * @param packDenomination the packDenomination to set
	 */
	public void setPackDenomination(Integer packDenomination) {
		this.packDenomination = packDenomination;
	}

	/**
	 * @return the denominationUnit
	 */
	public String getDenominationUnit() {
		return denominationUnit;
	}

	/**
	 * @param denominationUnit the denominationUnit to set
	 */
	public void setDenominationUnit(String denominationUnit) {
		this.denominationUnit = denominationUnit;
	}

	/**
	 * @return the molarChallengeRatio
	 */
	public String getMolarChallengeRatio() {
		return molarChallengeRatio;
	}

	/**
	 * @param molarChallengeRatio the molarChallengeRatio to set
	 */
	public void setMolarChallengeRatio(String molarChallengeRatio) {
		this.molarChallengeRatio = molarChallengeRatio;
	}

	/**
	 * @return the conjugationType
	 */
	public String getConjugationType() {
		return conjugationType;
	}

	/**
	 * @param conjugationType the conjugationType to set
	 */
	public void setConjugationType(String conjugationType) {
		this.conjugationType = conjugationType;
	}

	/**
	 * @return the checkoutDenomination
	 */
	public Integer getCheckoutDenomination() {
		return checkoutDenomination;
	}

	/**
	 * @param checkoutDenomination the checkoutDenomination to set
	 */
	public void setCheckoutDenomination(Integer checkoutDenomination) {
		this.checkoutDenomination = checkoutDenomination;
	}

	/**
	 * @return the clonality
	 */
	public String getClonality() {
		return clonality;
	}

	/**
	 * @param clonality the clonality to set
	 */
	public void setClonality(String clonality) {
		this.clonality = clonality;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
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
	 * @return the inventoryDetails
	 */
	public List<InventoryDetailV2> getInventoryDetails() {
		return inventoryDetails;
	}

	/**
	 * @param inventoryDetails the inventoryDetails to set
	 */
	public void setInventoryDetails(List<InventoryDetailV2> inventoryDetails) {
		this.inventoryDetails = inventoryDetails;
	}

	public void addInventoryDetail(InventoryDetailV2 inventoryDetail) {
		inventoryDetail.setInventory(this);
		this.inventoryDetails.add(inventoryDetail);
	}
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "inventoryId=" ).append(  inventoryId );
//        sb.append( ",name=" ).append(  name );
//        sb.append( ", category=" ).append(category);
//        sb.append( ", type=" ).append(type);
//        sb.append( ", active=" ).append( active );
//        sb.append( ", unitPrice=" ).append( unitPrice );
//        sb.append( ", catalogNumber=" ).append( catalogNumber );
//        sb.append( ", manufacturer=" ).append(getManufacturer());
//        sb.append( ", editedBy=" ).append(getEditedBy());
//        sb.append( ", modifiedOn=" ).append(getModifiedOn());
//        sb.append( ", supplierCatalogNumber=" ).append(getSupplierCatalogNumber());
//        sb.append( ", supplier=" ).append(getSupplier());
//        sb.append( ", subtype=" ).append(getSubtype());
//        sb.append( ", unit=" ).append(getUnit());
//        sb.append( ", amount=" ).append(getAmount());
//        sb.append( ", numberInUse=" ).append(getNumberInUse());
//        sb.append( ", containerSize=" ).append(getContainerSize());
//        sb.append( ", quantityThreshold=" ).append(getQuantityThreshold());
//        sb.append( ", supplierLink=" ).append(getSupplierLink());
//        sb.append( ", manufacturerLink=" ).append(getManufacturerLink());
//        sb.append( ", chemicalAbstractsServiceNumber=" ).append(getChemicalAbstractsServiceNumber());
//        sb.append( ", molecularWeight=" ).append(getMolecularWeight());
//        sb.append( ", compoundName=" ).append(getCompoundName());
//        sb.append( ", tradeName=" ).append(getTradeName());
//        sb.append( ", drugType=" ).append(getDrugType());
//        sb.append( ", packDenomination=" ).append(getPackDenomination());
//        sb.append( ", denominationUnit=" ).append(getDenominationUnit());
//        sb.append( ", molarChallengeRatio=" ).append(getMolarChallengeRatio());
//        sb.append( ", conjugationType=" ).append(getConjugationType());
//        sb.append( ", checkoutDenomination=" ).append(getCheckoutDenomination());
//        sb.append( ", clonality=" ).append(getClonality());
//        sb.append( ", host=" ).append(getHost());
//        sb.append( ", comment=" ).append(getComment());
//        if ( getInventoryDetails() != null ) {
//          for (InventoryDetailV2 id : getInventoryDetails()) {
//            sb.append(id.toString());
//          }
//        }
//        return sb.toString();
//    }

	/**
	 * @return the oldInventoryId
	 */
	public Long getOldInventoryId() {
		return oldInventoryId;
	}

	/**
	 * @param oldInventoryId the oldInventoryId to set
	 */
	public void setOldInventoryId(Long oldInventoryId) {
		this.oldInventoryId = oldInventoryId;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the inventoryId
	 */
	public long getInventoryId() {
		return inventoryId;
	}

	/**
	 * @param inventoryId the inventoryId to set
	 */
	public void setInventoryId(long inventoryId) {
		this.inventoryId = inventoryId;
	}

	private boolean hasInventoryDetail(InventoryDetailV2 inventoryDetailToCheck) {
		boolean found = false;
		if (inventoryDetailToCheck != null) {
			for (InventoryDetailV2 inventoryDetail : inventoryDetails) {
				if (inventoryDetail.getInventoryDetailId() == inventoryDetailToCheck.getInventoryDetailId()) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * @param addressId the addressId of the address to return
	 * @return CompanyShippingAddressV2 if the collection of shipping addresses has
	 *         a record with this id. If not found, return null
	 */
	private InventoryDetailV2 getInventoryDetail(long inventoryDetailsId) {
		InventoryDetailV2 aInventoryDetail = null;
		for (InventoryDetailV2 sa : inventoryDetails) {
			if (sa.getInventoryDetailId() == inventoryDetailsId) {
				aInventoryDetail = sa;
			}
		}
		return aInventoryDetail;
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
	 * @param containerSize the containerSize to set
	 */
	public void setContainerSize(Double containerSize) {
		this.containerSize = containerSize;
	}

	/**
	 * @return the numberInUse
	 */
	public Integer getNumberInUse() {
		return numberInUse;
	}

	/**
	 * @param numberInUse the numberInUse to set
	 */
	public void setNumberInUse(Integer numberInUse) {
		this.numberInUse = numberInUse;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(InventoryV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newInventoryDetailsStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("editedBy", this.getEditedBy(), obj.getEditedBy()).append("type", this.getType(), obj.getType())
				.append("name", this.getName(), obj.getName())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("amount", this.getAmount(), obj.getAmount())
				.append("numberInUse", this.getNumberInUse(), obj.getNumberInUse())
				.append("category", this.getCategory(), obj.getCategory())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("manufacturer", this.getManufacturer(), obj.getManufacturer())
				.append("supplierCatalogNumber", this.getSupplierCatalogNumber(), obj.getSupplierCatalogNumber())
				.append("supplier", this.getSupplier(), obj.getSupplier())
				.append("subtype", this.getSubtype(), obj.getSubtype()).append("unit", this.getUnit(), obj.getUnit())
				.append("quantityThreshold", this.getQuantityThreshold(), obj.getQuantityThreshold())
				.append("supplierLink", this.getSupplierLink(), obj.getSupplierLink())
				.append("manufacturerLink", this.getManufacturerLink(), obj.getManufacturerLink())
				.append("chemicalAbstractsServiceNumber", this.getChemicalAbstractsServiceNumber(),
						obj.getChemicalAbstractsServiceNumber())
				.append("compoundName", this.getCompoundName(), obj.getCompoundName())
				.append("tradeName", this.getTradeName(), obj.getTradeName())
				.append("drugType", this.getDrugType(), obj.getDrugType())
				.append("denominationUnit", this.getDenominationUnit(), obj.getDenominationUnit())
				.append("molarChallengeRatio", this.getMolarChallengeRatio(), obj.getMolarChallengeRatio())
				.append("conjugationType", this.getConjugationType(), obj.getConjugationType())
				.append("clonality", this.getClonality(), obj.getClonality())
				.append("host", this.getHost(), obj.getHost())
				.append("unitPrice", this.getUnitPrice(), obj.getUnitPrice())
				.append("containerSize", this.getContainerSize(), obj.getContainerSize())
				.append("molecularWeight", this.getMolecularWeight(), obj.getMolecularWeight())
				.append("active", this.isActive(), obj.isActive())
				.append("packDenomination", this.getPackDenomination(), obj.getPackDenomination())
				.append("checkoutDenomination", this.getCheckoutDenomination(), obj.getCheckoutDenomination())
				.append("comment", this.getComment(), obj.getComment())
				.append("oldInventoryId", this.getOldInventoryId(), obj.getOldInventoryId());

		int inventoryDetailIncrementer = 1;
		for (InventoryDetailV2 inventoryDetail : inventoryDetails) {
			if (obj.hasInventoryDetail(inventoryDetail)) {
				// check update diffs on same object
				InventoryDetailV2 otherInventoryDetail = obj.getInventoryDetail(inventoryDetail.getInventoryDetailId());
				DiffResult inventoryDetailDiffs = inventoryDetail.diff(otherInventoryDetail);
				db.append(String.format("inventoryDetail #%d (name:%s)", inventoryDetailIncrementer,
						inventoryDetail.getName()), inventoryDetailDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newInventoryDetailsStringBuilder.append(System.lineSeparator());
				newInventoryDetailsStringBuilder.append(String.format("inventoryDetail #%d is new - details : %s",
						inventoryDetailIncrementer, inventoryDetail.toString()));
			}
			inventoryDetailIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newInventoryDetailsStringBuilder.length() > 0) {
			diffStringBuilder.append(newInventoryDetailsStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}
}
