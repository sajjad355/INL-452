package com.internal.service.somruinternal.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.internal.service.somruinternal.model2.FolderLocation;
import com.internal.service.somruinternal.model2.UserV2;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FolderInput {

    private Long dbid;

    private Integer numberOfFolder;

    private String title;

    private String studyNumber;

    private UserV2 custodian;

    private UserV2 projectManager;

    private FolderLocation location;

    private UserV2 createdBy;

    private String remarks;

    private boolean isActive;

    //this input no used, barcode generated from backend
    //the property is here to maintain convention - input is same as entity
    private String barcode;

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }

    public Integer getNumberOfFolder() {
        return numberOfFolder;
    }

    public void setNumberOfFolder(Integer numberOfFolder) {
        this.numberOfFolder = numberOfFolder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudyNumber() {
        return studyNumber;
    }

    public void setStudyNumber(String studyNumber) {
        this.studyNumber = studyNumber;
    }

    public UserV2 getCustodian() {
        return custodian;
    }

    public void setCustodian(UserV2 custodian) {
        this.custodian = custodian;
    }

    public UserV2 getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserV2 projectManager) {
        this.projectManager = projectManager;
    }

    public FolderLocation getLocation() {
        return location;
    }

    public void setLocation(FolderLocation location) {
        this.location = location;
    }

    public UserV2 getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserV2 createdBy) {
        this.createdBy = createdBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String reamarks) {
        this.remarks = reamarks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    @Override
    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
