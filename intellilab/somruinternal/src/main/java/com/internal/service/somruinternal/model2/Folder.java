package com.internal.service.somruinternal.model2;

import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "il_folder")
public class Folder implements Diffable<Folder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    private String title;

    private String studyNumber;

    @ManyToOne
    @JoinColumn(name = "fk_custodian_id")
    private UserV2 custodian;

    @ManyToOne
    @JoinColumn(name = "fk_pm_id")
    private UserV2 projectManager;

    @ManyToOne
    @JoinColumn(name = "fk_location_id")
    private FolderLocation location;

    @ManyToOne
    @JoinColumn(name = "fk_creator_id")
    private UserV2 createdBy;

    private String remarks;

    private boolean isActive;

    private final String barcode = "F" + (new Random().nextInt(89999999) + 10000000);

    @CreationTimestamp
    private Date creationTime;

    @UpdateTimestamp
    private Date modificationTime;

    @JsonIgnore
    @OneToOne(mappedBy = "folder")
    private FolderCustodianTransfer transferRequest;

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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public FolderCustodianTransfer getTransferRequest() {
        return transferRequest;
    }

    public void setTransferRequest(FolderCustodianTransfer transferRequest) {
        this.transferRequest = transferRequest;
    }

    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    @Override
    public DiffResult diff(Folder obj) {
        return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
          .append("title", this.getTitle(), obj.getTitle() )
          .append("studyNumber", this.getStudyNumber(), obj.getStudyNumber())      
          .append("custodian", this.getCustodian(), obj.getCustodian() )
          .append("projectManager", this.getProjectManager(), obj.getProjectManager() )
          .append("location", this.getLocation(), obj.getLocation() )
          .append("createdBy", this.getCreatedBy(),  obj.getCreatedBy() )
          .append("remarks", this.getRemarks(),  obj.getRemarks() )
          .append("isActive", this.isActive(), obj.isActive() )
          .append("barcode", this.getBarcode(), obj.getBarcode() )          
          .append("transferRequest", this.getTransferRequest(),  obj.getTransferRequest() )

          .build();
    }
}
