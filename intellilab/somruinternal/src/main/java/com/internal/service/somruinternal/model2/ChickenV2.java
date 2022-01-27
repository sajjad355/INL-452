package com.internal.service.somruinternal.model2;

import java.util.ArrayList;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "il_chicken")
public class ChickenV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chickenId;

    @Column(nullable = false,length=50)
    private String chickenIdentifier;

    @Column(nullable = true)
    private Date dateOfBirth;

    @Column(nullable = true,length=50)
    private String immunogen;

    @Column(nullable = true,length=50)
    private String projectName;

    @Column(nullable = false,length=50)
    private String chickenStatus;

    @Column(nullable = false)
    private int totalEggs;

    @Column(nullable = false)
    private int eggsUsed;

    @Column(nullable = false)
    private int eggsDiscarded;

    @Column(nullable = true,length=50)
    private String latestTiter;

    @Column(nullable = true)
    private Date titerDate;

    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    @Column(nullable = true)
    private double sequence;

    @Column(nullable = false,length=50)
    private String immunizationStatus;

    @OneToMany(mappedBy="chicken")
    private List<EggV2> eggs = new ArrayList<EggV2>();

    @OneToMany(mappedBy = "chicken")
    private List<InjectionV2> injections = new ArrayList<InjectionV2>();



    public ChickenV2() {
        super();
    }

    /**
     * @return the chickenId
     */
    public long getChickenId() {
        return chickenId;
    }

    /**
     * @param chickenId the chickenId to set
     */
    public void setChickenId(long chickenId) {
        this.chickenId = chickenId;
    }

    /**
     * @return the chickenIdentifier
     */
    public String getChickenIdentifier() {
        return chickenIdentifier;
    }

    /**
     * @param chickenIdentifier the chickenIdentifier to set
     */
    public void setChickenIdentifier(String chickenIdentifier) {
        this.chickenIdentifier = chickenIdentifier;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the chickenStatus
     */
    public String getChickenStatus() {
        return chickenStatus;
    }

    /**
     * @param chickenStatus the chickenStatus to set
     */
    public void setChickenStatus(String chickenStatus) {
        this.chickenStatus = chickenStatus;
    }

    /**
     * @return the totalEggs
     */
    public int getTotalEggs() {
        return totalEggs;
    }

    /**
     * @param totalEggs the totalEggs to set
     */
    public void setTotalEggs(int totalEggs) {
        this.totalEggs = totalEggs;
    }

    /**
     * @return the eggsUsed
     */
    public int getEggsUsed() {
        return eggsUsed;
    }

    /**
     * @param eggsUsed the eggsUsed to set
     */
    public void setEggsUsed(int eggsUsed) {
        this.eggsUsed = eggsUsed;
    }

    /**
     * @return the eggsDiscarded
     */
    public int getEggsDiscarded() {
        return eggsDiscarded;
    }

    /**
     * @param eggsDiscarded the eggsDiscarded to set
     */
    public void setEggsDiscarded(int eggsDiscarded) {
        this.eggsDiscarded = eggsDiscarded;
    }

    /**
     * @return the latestTiter
     */
    public String getLatestTiter() {
        return latestTiter;
    }

    /**
     * @param latestTiter the latestTiter to set
     */
    public void setLatestTiter(String latestTiter) {
        this.latestTiter = latestTiter;
    }

    /**
     * @return the titerDate
     */
    public Date getTiterDate() {
        return titerDate;
    }

    /**
     * @param titerDate the titerDate to set
     */
    public void setTiterDate(Date titerDate) {
        this.titerDate = titerDate;
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
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    /**
     * @return the sequence
     */
    public double getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(double sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the immunizationStatus
     */
    public String getImmunizationStatus() {
        return immunizationStatus;
    }

    /**
     * @param immunizationStatus the immunizationStatus to set
     */
    public void setImmunizationStatus(String immunizationStatus) {
        this.immunizationStatus = immunizationStatus;
    }

    /**
     * @return the eggs
     */
    public List<EggV2> getEggs() {
        return eggs;
    }

    /**
     * @param eggs the eggs to set
     */
    public void setEggs(List<EggV2> eggs) {
        this.eggs = eggs;
    }

    /**
     * @param egg the egg to add to the chicken
     */
    public void addEgg(EggV2 egg) {
        this.eggs.add(egg);
        egg.setChicken(this);
    }

    /**
     * @return the injections
     */
    public List<InjectionV2> getInjections() {
        return injections;
    }

    /**
     * @param injections the injections to set
     */
    public void setInjections(List<InjectionV2> injections) {
        this.injections = injections;
    }

    /**
     * @param injection the injection to add to the chicken
     */
    public void addInjection(InjectionV2 injection) {
        this.injections.add(injection);
        injection.setChicken(this);
    }

}
