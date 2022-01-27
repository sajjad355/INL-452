package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.LocationV2;
import com.internal.service.somruinternal.model2.SettingV2;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface LocationRepositoryV2 extends JpaRepository<LocationV2, Long> {
    
    @Query(value = "SELECT location FROM LocationV2 location where location.locationName = :name") 
    public List<LocationV2> getLocationsByName(@Param("name") String name);
    
    @Query(value = "SELECT location FROM LocationV2 location where location.parentLocation is null order by location.locationName asc") 
    public List<LocationV2> getAllPrimaryLocationsOrderedByName();

	public LocationV2 findByLocationId(long checkoutLocationId);
    
}
