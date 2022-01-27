package com.internal.service.somruinternal.model2;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "il_egg_transfer")
public class EggTransferV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eggTransferId;

    @OneToOne(optional=false)
    @JoinColumn(name = "chicken_id")
    private ChickenV2 chicken;

    @OneToOne(optional=false)
    @JoinColumn(name = "egg_id")
    private EggV2 egg;

    @Column(nullable = false,length=50)
    private String action;

    @Column(nullable = false)
    private double eggUseAmount;

    @Column(nullable = false,length=50)
    private String destinationTable;

    @Column(nullable = false)
    private long destinationId;

    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    public EggTransferV2() {
        super();
    }


    /**
     * @return the eggTransferId
     */
    public long getEggTransferId() {
        return eggTransferId;
    }

    /**
     * @param eggTransferId the eggTransferId to set
     */
    public void setEggTransferId(long eggTransferId) {
        this.eggTransferId = eggTransferId;
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
     * @return the egg
     */
    public EggV2 getEgg() {
        return egg;
    }

    /**
     * @param egg the egg to set
     */
    public void setEgg(EggV2 egg) {
        this.egg = egg;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the eggUseAmount
     */
    public double getEggUseAmount() {
        return eggUseAmount;
    }

    /**
     * @param eggUseAmount the eggUseAmount to set
     */
    public void setEggUseAmount(double eggUseAmount) {
        this.eggUseAmount = eggUseAmount;
    }

    /**
     * @return the destinationTable
     */
    public String getDestinationTable() {
        return destinationTable;
    }

    /**
     * @param destinationTable the destinationTable to set
     */
    public void setDestinationTable(String destinationTable) {
        this.destinationTable = destinationTable;
    }

    /**
     * @return the destinationId
     */
    public long getDestinationId() {
        return destinationId;
    }

    /**
     * @param destinationId the destinationId to set
     */
    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
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
