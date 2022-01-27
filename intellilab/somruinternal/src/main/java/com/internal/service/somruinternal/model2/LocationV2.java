package com.internal.service.somruinternal.model2;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "il_location")
public class LocationV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    @Column(nullable = false,length=100)
    private String locationName;
    
    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;
    
    @OneToMany(mappedBy = "parentLocation",
               fetch = FetchType.EAGER, 
               orphanRemoval = true, 
               cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LocationV2> subLocations = new ArrayList<LocationV2>();

    @ManyToOne(optional=true)
    @JoinColumn(name="parent_location_id")
    @JsonBackReference
    private LocationV2 parentLocation;

    public LocationV2() {
        super();
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
     * @return the subLocations
     */
    public List<LocationV2> getSubLocations() {
        return subLocations;
    }

    /**
     * @param subLocations the subLocations to set
     */
    public void setSubLocations(List<LocationV2> subLocations) {
        this.subLocations = subLocations;
    }
    
    /**
     * @param subLocation the subLocation to add
     */
    public void addSubLocation(LocationV2 subLocation) {
        this.subLocations.add( subLocation );
        subLocation.setParentLocation(this);
    }
    
    /**
     * @return the parentLocation
     */
    public LocationV2 getParentLocation() {
        return parentLocation;
    }

    /**
     * @param parentLocation the parentLocation to set
     */
    public void setParentLocation(LocationV2 parentLocation) {
        this.parentLocation = parentLocation;
    }
    
    @Override
    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    public String diffCompare(LocationV2 obj) {
        StringBuilder diffStringBuilder = new StringBuilder();
        
        DiffBuilder db =  new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
         .append( "locationName", this.getLocationName(), obj.getLocationName())
         .append( "editedBy", this.getEditedBy(), obj.getEditedBy())
         .append( "modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
         .append( "subLocations", this.getSubLocations(), obj.getSubLocations())
         .append( "parentLocation", this.getParentLocation(), obj.getParentLocation());
          
        return diffStringBuilder.toString();
    }
    
   
}