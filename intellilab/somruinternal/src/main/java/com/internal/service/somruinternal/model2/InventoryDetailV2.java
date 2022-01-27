package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import net.bytebuddy.asm.Advice.This;

import java.util.ArrayList;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "il_inventory_detail")
public class InventoryDetailV2 implements Diffable<InventoryDetailV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long inventoryDetailId;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = true)
	private Date affinityPurificationPreparationDate;

	@Column(nullable = true, length = 50)
	private String columnPreparationNumber;

	@Column(nullable = true, length = 5000)
	private String comment;

	@Column(nullable = true, length = 50)
	private String concentration;

	@Column(nullable = true, length = 50)
	private String concentrationUnit;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = true)
	private Date expiryDate;

	@Column(nullable = false, length = 50)
	private String lotNumber;

	@Column(nullable = true)
	private Date receivedDate;

	@Column(nullable = true, length = 50)
	private String iggDepletion;

	@Column(nullable = true)
	private Integer numberInUse;

	@Column(nullable = true, length = 50)
	private String projectNumber;

	@Column(nullable = true, length = 50)
	private String purification;

	@Column(nullable = false)
	private boolean reserve;

	@Column(nullable = true)
	private Date retestDate;

	@Column(nullable = true, length = 50)
	private String unit;

	@Column(nullable = true)
	private Double containerSize;

	@Column(nullable = true, length = 50)
	private String columnUsage;

	@Column(nullable = true, length = 50)
	private String hazardLevel;

	@Column(nullable = true)
	private Double volume;

	@Column(nullable = true, length = 50)
	private String volumeUnit;

	@ManyToOne(optional = true)
	@JoinColumn(name = "location_id")
	private LocationV2 location;

	@ManyToOne(optional = true)
	@JoinColumn(name = "sub_location_id")
	private LocationV2 subLocation;

	@Column(nullable = true)
	private Integer immunizationCycleDay;

	@Column(nullable = true, length = 50)
	private String titerAssay;

	@Column(nullable = true)
	private Integer titer;

	@Column(nullable = true, length = 50)
	private String drugLotType;

	@Column(nullable = true, length = 50)
	private String modelNumber;

	@Column(nullable = true, length = 50)
	private String equipmentNumber;

	@Column(nullable = true, length = 50)
	private String referenceLotNumber;

	@Column(nullable = true, length = 50)
	private String batchNumber;

	@Column(nullable = true, length = 50)
	private String storeTemperature;

	@Column(nullable = true, length = 50)
	private String purity;

	@Column(nullable = true, length = 50)
	private String host;

	@Column(nullable = false)
	private boolean reconstituted;

	@Column(nullable = true, length = 5000)
	private String checkoutPurpose;

	@Column(nullable = false)
	@org.hibernate.annotations.ColumnDefault("true")
	private boolean meetsAcceptanceCriteria;

	@OneToMany(mappedBy = "inventoryDetail", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InventoryDetailVialV2> inventoryDetailVials = new ArrayList<InventoryDetailVialV2>();

	@ManyToOne(optional = false)
	@JoinColumn(name = "inventory_id")
	@JsonBackReference
	private InventoryV2 inventory;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "receive_user_id")
	private UserV2 receiveUser;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "reserve_client_id")
	private ClientCompanyV2 reserveForClient;

	public InventoryDetailV2() {
		super();
	}

	public InventoryDetailV2(InventoryDetailV2 other) {
		super();
		this.setActive(other.isActive());
		this.setAffinityPurificationPreparationDate(other.getAffinityPurificationPreparationDate());
		this.setAmount(other.getAmount());
		this.setBatchNumber(other.getBatchNumber());
		this.setCheckoutPurpose(other.getCheckoutPurpose());
		this.setColumnPreparationNumber(other.getColumnPreparationNumber());
		this.setColumnUsage(other.getColumnUsage());
		this.setComment(other.getComment());
		this.setConcentration(other.getConcentration());
		this.setConcentrationUnit(other.getConcentrationUnit());
		this.setContainerSize(other.getContainerSize());
		this.setDrugLotType(other.getDrugLotType());
		this.setEditedBy(other.getEditedBy());
		this.setEquipmentNumber(other.getEquipmentNumber());
		this.setExpiryDate(other.getExpiryDate());
		this.setHazardLevel(other.getHazardLevel());
		this.setHost(other.getHost());
		this.setIggDepletion(other.getIggDepletion());
		this.setImmunizationCycleDay(other.getImmunizationCycleDay());
		this.setInventory(other.getInventory());
		this.setInventoryDetailId(other.getInventoryDetailId());
		this.setLocation(other.getLocation());
		this.setLotNumber(other.getLotNumber());
		this.setModelNumber(other.getModelNumber());
		this.setModifiedOn(other.getModifiedOn());
		this.setName(other.getName());
		this.setNumberInUse(other.getNumberInUse());
		this.setProjectNumber(other.getProjectNumber());
		this.setPurification(other.getPurification());
		this.setPurity(other.getPurity());
		this.setReceiveUser(other.getReceiveUser());
		this.setReceivedDate(other.getReceivedDate());
		this.setReconstituted(other.isReconstituted());
		this.setReferenceLotNumber(other.getReferenceLotNumber());
		this.setReserve(other.isReserve());
		this.setReserveForClient(other.getReserveForClient());
		this.setRetestDate(other.getRetestDate());
		this.setStoreTemperature(other.getStoreTemperature());
		this.setSubLocation(other.getSubLocation());
		this.setTiter(other.getTiter());
		this.setTiterAssay(other.getTiterAssay());
		this.setUnit(other.getUnit());
		this.setVolume(other.getVolume());
		this.setVolumeUnit(other.getVolumeUnit());

		if (inventoryDetailVials != null) {
			inventoryDetailVials.forEach((idv) -> {
				this.addInventoryDetailVials(new InventoryDetailVialV2(idv));
			});
		}
	}

	/**
	 * @return the inventoryDetailId
	 */
	public long getInventoryDetailId() {
		return inventoryDetailId;
	}

	/**
	 * @param inventoryDetailId the inventoryDetailId to set
	 */
	public void setInventoryDetailId(long inventoryDetailId) {
		this.inventoryDetailId = inventoryDetailId;
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
	 * @return the affinityPurificationPreparationDate
	 */
	public Date getAffinityPurificationPreparationDate() {
		return affinityPurificationPreparationDate;
	}

	/**
	 * @param affinityPurificationPreparationDate the
	 *                                            affinityPurificationPreparationDate
	 *                                            to set
	 */
	public void setAffinityPurificationPreparationDate(Date affinityPurificationPreparationDate) {
		this.affinityPurificationPreparationDate = affinityPurificationPreparationDate;
	}

	/**
	 * @return the columnPreparationNumber
	 */
	public String getColumnPreparationNumber() {
		return columnPreparationNumber;
	}

	/**
	 * @param columnPreparationNumber the columnPreparationNumber to set
	 */
	public void setColumnPreparationNumber(String columnPreparationNumber) {
		this.columnPreparationNumber = columnPreparationNumber;
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
	 * @return the concentration
	 */
	public String getConcentration() {
		return concentration;
	}

	/**
	 * @param concentration the concentration to set
	 */
	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}

	/**
	 * @return the concentrationUnit
	 */
	public String getConcentrationUnit() {
		return concentrationUnit;
	}

	/**
	 * @param concentrationUnit the concentrationUnit to set
	 */
	public void setConcentrationUnit(String concentrationUnit) {
		this.concentrationUnit = concentrationUnit;
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
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the lotNumber
	 */
	public String getLotNumber() {
		return lotNumber;
	}

	/**
	 * @param lotNumber the lotNumber to set
	 */
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the iggDepletion
	 */
	public String getIggDepletion() {
		return iggDepletion;
	}

	/**
	 * @param iggDepletion the iggDepletion to set
	 */
	public void setIggDepletion(String iggDepletion) {
		this.iggDepletion = iggDepletion;
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

	/**
	 * @return the projectNumber
	 */
	public String getProjectNumber() {
		return projectNumber;
	}

	/**
	 * @param projectNumber the projectNumber to set
	 */
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	/**
	 * @return the purification
	 */
	public String getPurification() {
		return purification;
	}

	/**
	 * @param purification the purification to set
	 */
	public void setPurification(String purification) {
		this.purification = purification;
	}

	/**
	 * @return the reserve
	 */
	public boolean isReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(boolean reserve) {
		this.reserve = reserve;
	}

	/**
	 * @return the retestDate
	 */
	public Date getRetestDate() {
		return retestDate;
	}

	/**
	 * @param retestDate the retestDate to set
	 */
	public void setRetestDate(Date retestDate) {
		this.retestDate = retestDate;
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
	public void setContainerSize(Double containerSize) {
		this.containerSize = containerSize;
	}

	/**
	 * @return the columnUsage
	 */
	public String getColumnUsage() {
		return columnUsage;
	}

	/**
	 * @param columnUsage the columnUsage to set
	 */
	public void setColumnUsage(String columnUsage) {
		this.columnUsage = columnUsage;
	}

	/**
	 * @return the volume
	 */
	public Double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Double volume) {
		this.volume = volume;
	}

	/**
	 * @return the volumeUnit
	 */
	public String getVolumeUnit() {
		return volumeUnit;
	}

	/**
	 * @param volumeUnit the volumeUnit to set
	 */
	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	/**
	 * @return the location
	 */
	public LocationV2 getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationV2 location) {
		this.location = location;
	}

	/**
	 * @return the subLocation
	 */
	public LocationV2 getSubLocation() {
		return subLocation;
	}

	/**
	 * @param subLocation the subLocation to set
	 */
	public void setSubLocation(LocationV2 subLocation) {
		this.subLocation = subLocation;
	}

	/**
	 * @return the immunizationCycleDay
	 */
	public Integer getImmunizationCycleDay() {
		return immunizationCycleDay;
	}

	/**
	 * @param immunizationCycleDay the immunizationCycleDay to set
	 */
	public void setImmunizationCycleDay(Integer immunizationCycleDay) {
		this.immunizationCycleDay = immunizationCycleDay;
	}

	/**
	 * @return the titerAssay
	 */
	public String getTiterAssay() {
		return titerAssay;
	}

	/**
	 * @param titerAssay the titerAssay to set
	 */
	public void setTiterAssay(String titerAssay) {
		this.titerAssay = titerAssay;
	}

	/**
	 * @return the drugLotType
	 */
	public String getDrugLotType() {
		return drugLotType;
	}

	/**
	 * @param drugLotType the drugLotType to set
	 */
	public void setDrugLotType(String drugLotType) {
		this.drugLotType = drugLotType;
	}

	/**
	 * @return the modelNumber
	 */
	public String getModelNumber() {
		return modelNumber;
	}

	/**
	 * @param modelNumber the modelNumber to set
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	/**
	 * @return the equipmentNumber
	 */
	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	/**
	 * @param equipmentNumber the equipmentNumber to set
	 */
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	/**
	 * @return the referernceLotNumber
	 */
	public String getReferenceLotNumber() {
		return referenceLotNumber;
	}

	/**
	 * @param referenceLotNumber the referernceLotNumber to set
	 */
	public void setReferenceLotNumber(String referenceLotNumber) {
		this.referenceLotNumber = referenceLotNumber;
	}

	/**
	 * @return the inventoryDetailVials
	 */
	public List<InventoryDetailVialV2> getInventoryDetailVials() {
		return inventoryDetailVials;
	}

	/**
	 * @param inventoryDetailVials the inventoryDetailVials to set
	 */
	public void setInventoryDetailVials(List<InventoryDetailVialV2> inventoryDetailVials) {
		this.inventoryDetailVials = inventoryDetailVials;
	}

	/**
	 * @param vial the inventoryDetailVial to add
	 */
	public void addInventoryDetailVials(InventoryDetailVialV2 vial) {
		this.inventoryDetailVials.add(vial);
		vial.setInventoryDetail(this);
	}

	/**
	 * @return the inventory
	 */
	public InventoryV2 getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(InventoryV2 inventory) {
		this.inventory = inventory;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("inventoryDetailId=").append(inventoryDetailId);
//        sb.append( ", amount=").append( amount );
//        sb.append( ", affinityPurificationPreparationDate=").append( affinityPurificationPreparationDate);
//        sb.append(", columnPreparationNumber=").append( columnPreparationNumber );
//        sb.append(", comment=" ).append( comment );
//        sb.append(", concentration=" ).append( concentration );
//        sb.append(", concentrationUnit=" ).append( concentrationUnit );
//        sb.append(", modifyOn=" ).append( modifiedOn );
//        sb.append(", editedBy=" ).append( editedBy );
//        sb.append(", expiryDate=" ).append( expiryDate );
//        sb.append(", lotNumber=" ).append( lotNumber );
//        sb.append(", receivedDate=" ).append( receivedDate );
//        sb.append(", iggDepletion=" ).append( iggDepletion );
//        sb.append(", numberInUse=" ).append( numberInUse );
//        sb.append(", projectNumber=" ).append( projectNumber );
//        sb.append(", purification=" ).append( purification );
//        sb.append(", reconstituted=" ).append( reconstituted );
//        sb.append(", reserve=" ).append( reserve );
//        sb.append(", retestDate=" ).append( retestDate );
//        sb.append(", unit=" ).append( unit );
//        sb.append(", containerSize=" ).append( containerSize );
//        sb.append(", columnUsage=" ).append( columnUsage );
//        sb.append(", volume=" ).append( volume );
//        sb.append(", volumeUnit=" ).append( volumeUnit );
//        if ( this.location != null )
//            sb.append(", location=" ).append( location.getLocationName() );
//        if ( this.subLocation != null )
//            sb.append(", subLocation=" ).append( subLocation.getLocationName() );
//        sb.append(", immunizationCycleDay=" ).append( immunizationCycleDay );
//        sb.append(", titerAssay=" ).append( titerAssay );
//        sb.append(", hazardLevel=" ).append( hazardLevel );
//        sb.append(", titer=" ).append( titer );
//        sb.append(", drugLotType=" ).append( drugLotType );
//        sb.append(", modelNumber=" ).append( modelNumber );
//        sb.append(", equipmentNumber=" ).append( equipmentNumber );
//        sb.append(", referernceLotNumber=" ).append( referenceLotNumber );
//        sb.append(", host=" ).append( host );
//        sb.append(", purity=" ).append( purity );
//        sb.append(", batchNumber=" ).append( batchNumber );
//        sb.append(", storeTemperature=" ).append( storeTemperature );
//        sb.append(", checkoutPurpose=" ).append( checkoutPurpose );
//
//        
//        
//        if ( inventoryDetailVials != null ) {
//            inventoryDetailVials.forEach((idv) -> {
//               sb.append( idv.toString() );
//            });
//        }
//        return sb.toString();
//    }

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

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the storeTemperature
	 */
	public String getStoreTemperature() {
		return storeTemperature;
	}

	/**
	 * @param storeTemperature the storeTemperature to set
	 */
	public void setStoreTemperature(String storeTemperature) {
		this.storeTemperature = storeTemperature;
	}

	/**
	 * @return the purity
	 */
	public String getPurity() {
		return purity;
	}

	/**
	 * @param purity the purity to set
	 */
	public void setPurity(String purity) {
		this.purity = purity;
	}

	/**
	 * @return the titer
	 */
	public Integer getTiter() {
		return titer;
	}

	/**
	 * @param titer the titer to set
	 */
	public void setTiter(Integer titer) {
		this.titer = titer;
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
	 * @return the reconstituted
	 */
	public boolean isReconstituted() {
		return reconstituted;
	}

	/**
	 * @param reconstituted the reconstituted to set
	 */
	public void setReconstituted(boolean reconstituted) {
		this.reconstituted = reconstituted;
	}

	/**
	 * @return the hazardLevel
	 */
	public String getHazardLevel() {
		return hazardLevel;
	}

	/**
	 * @param hazardLevel the hazardLevel to set
	 */
	public void setHazardLevel(String hazardLevel) {
		this.hazardLevel = hazardLevel;
	}

	/**
	 * @return the checkoutPurpose
	 */
	public String getCheckoutPurpose() {
		return checkoutPurpose;
	}

	/**
	 * @param checkoutPurpose the checkoutPurpose to set
	 */
	public void setCheckoutPurpose(String checkoutPurpose) {
		this.checkoutPurpose = checkoutPurpose;
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
	 * @return the meetsAcceptanceCriteria
	 */
	public boolean isMeetsAcceptanceCriteria() {
		return meetsAcceptanceCriteria;
	}

	/**
	 * @param meetsAcceptanceCriteria the meetsAcceptanceCriteria to set
	 */
	public void setMeetsAcceptanceCriteria(boolean meetsAcceptanceCriteria) {
		this.meetsAcceptanceCriteria = meetsAcceptanceCriteria;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(InventoryDetailV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName()).append("active", this.isActive(), obj.isActive())
				.append("amount", this.getAmount(), obj.getAmount())
				.append("affinityPurificationPreparationDate", this.getAffinityPurificationPreparationDate(),
						obj.getAffinityPurificationPreparationDate())
				.append("columnPreparationNumber", this.getColumnPreparationNumber(), obj.getColumnPreparationNumber())
				.append("comment", this.getComment(), obj.getComment())
				.append("concentration", this.getConcentration(), obj.getConcentration())
				.append("concentrationUnit", this.getConcentrationUnit(), obj.getConcentrationUnit())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("expiryDate", this.getExpiryDate(), obj.getExpiryDate())
				.append("lotNumber", this.getLotNumber(), obj.getLotNumber())
				.append("receivedDate", this.getReceivedDate(), obj.getReceivedDate())
				.append("iggDepletion", this.getIggDepletion(), obj.getIggDepletion())
				.append("numberInUse", this.getNumberInUse(), obj.getNumberInUse())
				.append("projectNumber", this.getProjectNumber(), obj.getProjectNumber())
				.append("purification", this.getPurification(), obj.getPurification())
				.append("reserve", this.isReserve(), obj.isReserve())
				.append("retestDate", this.getRetestDate(), obj.getRetestDate())
				.append("unit", this.getUnit(), obj.getUnit())
				.append("containerSize", this.getContainerSize(), obj.getContainerSize())
				.append("columnUsage", this.getColumnUsage(), obj.getColumnUsage())
				.append("hazardLevel", this.getHazardLevel(), obj.getHazardLevel())
				.append("volume", this.getVolume(), obj.getVolume())
				.append("volumeUnit", this.getVolumeUnit(), obj.getVolumeUnit())
				.append("location", this.getLocation(), obj.getLocation())
				.append("subLocation", this.getSubLocation(), obj.getSubLocation())
				.append("immunizationCycleDay", this.getImmunizationCycleDay(), obj.getImmunizationCycleDay())
				.append("titerAssay", this.getTiterAssay(), obj.getTiterAssay())
				.append("titer", this.getTiter(), obj.getTiter())
				.append("drugLotType", this.getDrugLotType(), obj.getDrugLotType())
				.append("modelNumber", this.getModelNumber(), obj.getModelNumber())
				.append("equipmentNumber", this.getEquipmentNumber(), obj.getEquipmentNumber())
				.append("referenceLotNumber", this.getReferenceLotNumber(), obj.getReferenceLotNumber())
				.append("batchNumber", this.getBatchNumber(), obj.getBatchNumber())
				.append("storeTemperature", this.getStoreTemperature(), obj.getStoreTemperature())
				.append("purity", this.getPurity(), obj.getPurity()).append("host", this.getHost(), obj.getHost())
				.append("reconstituted", this.isReconstituted(), obj.isReconstituted())
				.append("checkoutPurpose", this.getCheckoutPurpose(), obj.getCheckoutPurpose())
				.append("meetsAcceptanceCriteria", this.isMeetsAcceptanceCriteria(), obj.isMeetsAcceptanceCriteria())
				.append("inventoryDetailVials ", this.getInventoryDetailVials(), obj.getInventoryDetailVials())
				.append("inventory", this.getInventory(), obj.getInventory())
				.append("receiveUser", this.getReceiveUser(), obj.getReceiveUser())
				.append("reserveForClient", this.getReceivedDate(), obj.getReceivedDate()).build();

	}

}
