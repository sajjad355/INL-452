package com.internal.service.somruinternal.model2;

import javax.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "il_egg")
public class EggV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eggId;

    @ManyToOne(optional=false)
    @JoinColumn(name="chicken_id")
    private ChickenV2 chicken;

    @Column(nullable = true,length=50)
    private String immunogen;

    @Column(nullable = true,length=50)
    private String eggPart;

    @Column(nullable = true)
    private Date collectionDate;

    @Column(nullable = true)
    private Date firstInjectionDate;

    @Column(nullable = true)
    private int daysAfterImmunization;

    @Column(nullable = true)
    private Date firstLayEggDate;

    @Column(nullable = true)
    private Date lastLayEggDate;

    @Column(nullable = true,length=50)
    private String amount;

    @ManyToOne(optional=false)
    @JoinColumn(name = "location_id")
    private LocationV2 location;

    @ManyToOne(optional=true)
    @JoinColumn(name = "sub_location_id")
    private LocationV2 subLocation;

    @Column(nullable = true)
    private Date frozenDate;

    @Column(nullable = true,length=50)
    private String addSucrose;

    @Column(nullable = true,length=50)
    private String titer;

    @Column(nullable = false)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    public EggV2() {
        super();
    }


    /**
     * @return the eggId
     */
    public long getEggId() {
        return eggId;
    }

    /**
     * @param eggId the eggId to set
     */
    public void setEggId(long eggId) {
        this.eggId = eggId;
    }

    /**
     * @return the chicken
     */
    public ChickenV2 getChicken() {
        return chicken;
    }

    /**
     * @param chicken the chicken to set
     */
    public void setChicken(ChickenV2 chicken) {
        this.chicken = chicken;
    }

    /**
     * @return the immunogen
     */
    public String getImmunogen() {
        return immunogen;
    }

    /**
     * @param immunogen the immunogen to set
     */
    public void setImmunogen(String immunogen) {
        this.immunogen = immunogen;
    }

    /**
     * @return the eggPart
     */
    public String getEggPart() {
        return eggPart;
    }

    /**
     * @param eggPart the eggPart to set
     */
    public void setEggPart(String eggPart) {
        this.eggPart = eggPart;
    }

    /**
     * @return the collectionDate
     */
    public Date getCollectionDate() {
        return collectionDate;
    }

    /**
     * @param collectionDate the collectionDate to set
     */
    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    /**
     * @return the firstInjectionDate
     */
    public Date getFirstInjectionDate() {
        return firstInjectionDate;
    }

    /**
     * @param firstInjectionDate the firstInjectionDate to set
     */
    public void setFirstInjectionDate(Date firstInjectionDate) {
        this.firstInjectionDate = firstInjectionDate;
    }

    /**
     * @return the daysAfterImmunization
     */
    public int getDaysAfterImmunization() {
        return daysAfterImmunization;
    }

    /**
     * @param daysAfterImmunization the daysAfterImmunization to set
     */
    public void setDaysAfterImmunization(int daysAfterImmunization) {
        this.daysAfterImmunization = daysAfterImmunization;
    }

    /**
     * @return the firstLayEggDate
     */
    public Date getFirstLayEggDate() {
        return firstLayEggDate;
    }

    /**
     * @param firstLayEggDate the firstLayEggDate to set
     */
    public void setFirstLayEggDate(Date firstLayEggDate) {
        this.firstLayEggDate = firstLayEggDate;
    }

    /**
     * @return the lastLayEggDate
     */
    public Date getLastLayEggDate() {
        return lastLayEggDate;
    }

    /**
     * @param lastLayEggDate the lastLayEggDate to set
     */
    public void setLastLayEggDate(Date lastLayEggDate) {
        this.lastLayEggDate = lastLayEggDate;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
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
     * @return the frozenDate
     */
    public Date getFrozenDate() {
        return frozenDate;
    }

    /**
     * @param frozenDate the frozenDate to set
     */
    public void setFrozenDate(Date frozenDate) {
        this.frozenDate = frozenDate;
    }

    /**
     * @return the addSucrose
     */
    public String getAddSucrose() {
        return addSucrose;
    }

    /**
     * @param addSucrose the addSucrose to set
     */
    public void setAddSucrose(String addSucrose) {
        this.addSucrose = addSucrose;
    }

    /**
     * @return the titer
     */
    public String getTiter() {
        return titer;
    }

    /**
     * @param titer the titer to set
     */
    public void setTiter(String titer) {
        this.titer = titer;
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





}
