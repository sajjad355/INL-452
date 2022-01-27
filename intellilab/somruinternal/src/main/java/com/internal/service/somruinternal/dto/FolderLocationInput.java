package com.internal.service.somruinternal.dto;

import java.util.Date;
import org.apache.commons.lang3.builder.*;

import com.internal.service.somruinternal.model2.UserV2;

public class FolderLocationInput {

    private Long dbid;

    private String title;

    private Date modifiedOn;

    private UserV2 employee;

    private UserV2 projectManager;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public UserV2 getEmployee() {
        return employee;
    }

    public void setEmployee(UserV2 employee) {
        this.employee = employee;
    }

    public UserV2 getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserV2 projectManager) {
        this.projectManager = projectManager;
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
