package com.internal.service.somruinternal.controller2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import java.util.List;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/locationv2")
public class LocationControlV2 extends ParentControl {
    
    @Autowired
    LocationRepositoryV2 locationRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(LocationControlV2.class);
    
    /**
     *  get all locations sorted by location name in ascending alphabetical order
     */
    @GetMapping("/all")
    public List<LocationV2> getAllLocations() {
        LOGGER.info( "getAllLocations received request");
        List<LocationV2> locationList = locationRepo.getAllPrimaryLocationsOrderedByName();
        LOGGER.info( String.format("Retrieved %d location rows", locationList.size()));
        return locationList;
    }
    
    /**
     *  get a reference count of all objects/table rows referring to this location in order to 
     *  determine whether it can be safely deleted
     *  @param - locationId - locationId to retrieve count of referenced obhects
     *  @return - count of objects linked to this location
     */
    @GetMapping("/getReferenceCount/{locationId}/")
    public int getReferenceCount(@PathVariable(value = "locationId") long locationId) {
        //TODO: implemment this when we have some of the other parts that reference
        // locations worked out.
        LOGGER.info( "getReferenceCount received request - implementation TBD - default value returned for now");
        return 0;
    }
    
    /**
     *  Save the passed location and sublocations to the database.
     *  @param location the location to insert or update
     *  
     *  
     */
    
    private LocationV2 loadLocation( Long locationId ) {
    	LocationV2 location = locationRepo.findByLocationId(locationId);
        if (location == null) {
            LOGGER.info( "location not found");
            throw new EntityNotFoundException(ClientCompanyV2.class, "id", locationId.toString());
        }
//        Hibernate.initialize(client.getBillingAddress());
//        Hibernate.initialize(client.getShippingAddresses());
//        Hibernate.initialize(client.getClientContacts());
        return location;
    }
    
    @PostMapping("/save")
    @Transactional(rollbackFor=Exception.class)
    public LocationV2 saveLocation(@Valid @RequestBody LocationV2 location) {
        LOGGER.info( String.format( 
           "saveLocation received request - details %s:", location ));
        
        String action;
        String auditTrailRecord;
        if (location.getLocationId() > 0) {
        	LocationV2 previousLocationDetails = this.loadLocation(location.getLocationId());
            action = "update location";
            auditTrailRecord = getDiffs(location, previousLocationDetails);
        }
        else {
            action = "insert location";
            auditTrailRecord = location.toString();
        }
        
        LocationV2 savedLocation = locationRepo.save(location);
        LOGGER.info("Completed saving location");
        super.saveUserHistory(location.getEditedBy(), "Location", "saveLocation", savedLocation.toString());
        return savedLocation;
    }
    
    private String getDiffs(LocationV2 lhs, LocationV2 rhs ) {
        if ( (lhs == null) || (rhs == null) )
            throw new RuntimeException("Error: Trying to compare two Locationv2 objects and at least one is null");
        String diffs = lhs.diffCompare(rhs);
        LOGGER.info(String.format( "Locationv2 GET DIFFS RESULT: %s", diffs));
        return diffs;
        
    }

}
