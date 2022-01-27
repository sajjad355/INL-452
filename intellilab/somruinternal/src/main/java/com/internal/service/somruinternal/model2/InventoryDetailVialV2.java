package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "il_inventory_detail_vial")
public class InventoryDetailVialV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryDetailVialId;

    @ManyToOne(optional=false)
    @JoinColumn(name = "inventory_detail_id")
    @JsonBackReference
    private InventoryDetailV2 inventoryDetail;

    @Column(nullable = false,length=50)
    private String vialNumber;

    @Column(nullable = false)
    private Double volume;

    @Column(nullable = false,length=50)
    private String unit;

    @Column(nullable = false,length=50)
    private String status;

    @Column(nullable = false)
    private Date modifyOn;

    @Column(nullable = false,length=50)
    private String editedBy;
    
    @Column(nullable = true, length = 5000)
    private String checkoutPurpose;

    @ManyToOne(optional=false)
    @JoinColumn(name = "location_id")
    private LocationV2 location;

    @ManyToOne(optional=true)
    @JoinColumn(name = "sub_location_id")
    private LocationV2 subLocation;

    public InventoryDetailVialV2() {
        super();
    }
    
    public InventoryDetailVialV2(InventoryDetailVialV2 other) {
        super();
        this.setCheckoutPurpose(other.getCheckoutPurpose());
        this.setEditedBy(other.getEditedBy());
        this.setInventoryDetail(other.getInventoryDetail());
        this.setInventoryDetailVialId(other.getInventoryDetailVialId());
        this.setLocation(other.getLocation());
        this.setModifyOn(other.getModifyOn());
        this.setStatus(other.getStatus());
        this.setSubLocation(other.getSubLocation());
        this.setUnit(other.getUnit());
        this.setVialNumber(other.getVialNumber());
        this.setVolume(other.getVolume());
    }

    /**
     * @return the inventoryDetailVialId
     */
    public long getInventoryDetailVialId() {
        return inventoryDetailVialId;
    }

    /**
     * @param inventoryDetailVialId the inventoryDetailVialId to set
     */
    public void setInventoryDetailVialId(long inventoryDetailVialId) {
        this.inventoryDetailVialId = inventoryDetailVialId;
    }

    /**
     * @return the inventoryDetail
     */
    public InventoryDetailV2 getInventoryDetail() {
        return inventoryDetail;
    }

    /**
     * @param inventoryDetail the inventoryDetail to set
     */
    public void setInventoryDetail(InventoryDetailV2 inventoryDetail) {
        this.inventoryDetail = inventoryDetail;
    }

    /**
     * @return the vialNumber
     */
    public String getVialNumber() {
        return vialNumber;
    }

    /**
     * @param vialNumber the vialNumber to set
     */
    public void setVialNumber(String vialNumber) {
        this.vialNumber = vialNumber;
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
     * @return the modifyOn
     */
    public Date getModifyOn() {
        return modifyOn;
    }

    /**
     * @param modifyOn the modifyOn to set
     */
    public void setModifyOn(Date modifyOn) {
        this.modifyOn = modifyOn;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "inventoryDetailVialId=");
        sb.append( inventoryDetailVialId );
        sb.append( ",vialNumber=");
        sb.append( vialNumber );
        sb.append( ",volume=");
        sb.append( volume );
        sb.append( ",unit=");
        sb.append( unit );
        sb.append( ",status=");
        sb.append( status );
        sb.append( ",modifyOn=");
        sb.append( modifyOn );
        sb.append( ",editedBy=");
        sb.append( editedBy );
        sb.append( ",checkoutPurpose=");
        sb.append( checkoutPurpose );
        if ( this.location != null )
            sb.append(", location=" ).append( location.getLocationName() );
        if ( this.subLocation != null )
            sb.append(", subLocation=" ).append( subLocation.getLocationName() );
        return sb.toString();
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




}
