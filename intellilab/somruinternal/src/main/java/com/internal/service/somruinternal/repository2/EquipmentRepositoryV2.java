package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internal.service.somruinternal.model2.EquipmentV2;

public interface EquipmentRepositoryV2 extends JpaRepository<EquipmentV2, Long> {

	EquipmentV2 findByEquipmentId(Long equipmentId);

	@Query(value = "Select equipment from EquipmentV2 equipment Where " + "(UPPER(equipment.name) LIKE %:searchKey%)"
			+ " and active = TRUE ORDER By equipment.modifiedOn DESC")
	public List<EquipmentV2> searchActive(@Param(value = "searchKey") String searckKey, Pageable pageable);

	@Query(value = "Select equipment from EquipmentV2 equipment Where " + "UPPER(equipment.name) LIKE %:searchKey%"
			+ " and active = FALSE ORDER By equipment.modifiedOn DESC")
	public List<EquipmentV2> searchInactive(@Param(value = "searchKey") String searckKey, Pageable pageable);

	public Page<EquipmentV2> findByActive(boolean active, Pageable pageable);

	@Query(value = "Select COUNT(equipment) from EquipmentV2 equipment Where "
			+ "( UPPER(equipment.name) LIKE %:searchKey%)" + " and active = TRUE")
	public Long searchCountActive(@Param("searchKey") String searchKey);

	@Query(value = "Select COUNT(equipment) from EquipmentV2 equipment Where "
			+ "UPPER(equipment.name) LIKE %:searchKey%")
	public Long searchCount(@Param("searchKey") String searchKey);
        
        @Query(value = "Select equipment from EquipmentV2 equipment where " +
		        "  equipment.active = TRUE and " +
                        "   ((equipment.calibrationPerformed < CURDATE() - 7 AND equipment.calibrationScheduleInterval = 'Weekly' ) or " +
                        "    (equipment.calibrationPerformed < CURDATE() - 31 AND equipment.calibrationScheduleInterval = 'Monthly' ) or " +
                        "    (equipment.calibrationPerformed < CURDATE() - 365 AND equipment.calibrationScheduleInterval = 'Yearly' ) or " +
                        "    (equipment.lastMaintenancePerformedDate < CURDATE() - 1 AND equipment.maintenanceInterval = 'Daily' ) or " + 
                        "    (equipment.lastMaintenancePerformedDate < CURDATE() - 7 AND equipment.maintenanceInterval = 'Weekly' ) or " + 
                        "    (equipment.lastMaintenancePerformedDate < CURDATE() - 31 AND equipment.maintenanceInterval = 'Monthly' ) or " + 
                        "    (equipment.lastMaintenancePerformedDate < CURDATE() - 365 AND equipment.maintenanceInterval = 'Yearly' ))") 
        public List<EquipmentV2> loadMaintenanceOrCalibrateDue();

}
