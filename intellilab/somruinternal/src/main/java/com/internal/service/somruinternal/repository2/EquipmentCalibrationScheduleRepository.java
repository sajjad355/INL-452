package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.service.somruinternal.model2.EquipmentCalibrationSchedule;
import com.internal.service.somruinternal.model2.EquipmentMaintSchedule;

public interface EquipmentCalibrationScheduleRepository extends JpaRepository<EquipmentCalibrationSchedule, Long> {

	EquipmentCalibrationSchedule findByEquipmentCalibrationScheduleId(Long dbId);

}
