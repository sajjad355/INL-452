package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.EquipmentMaintSchedule;

@Repository
public interface EquipmentMaintScheduleRepository extends JpaRepository<EquipmentMaintSchedule, Long> {

	EquipmentMaintSchedule findByEquipmentMaintScheduleId(Long dbId);

}
