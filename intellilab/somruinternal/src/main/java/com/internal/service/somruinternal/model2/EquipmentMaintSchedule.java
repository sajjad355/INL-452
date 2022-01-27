package com.internal.service.somruinternal.model2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "il_equipment_maint_schedule")
public class EquipmentMaintSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long equipmentMaintScheduleId;

	@Column(nullable = false, length = 5000)
	private String name;

	@Column(nullable = false)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	public EquipmentMaintSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentMaintSchedule(long equipmentMaintScheduleId, String name, String editedBy, Date modifiedOn) {
		super();
		this.equipmentMaintScheduleId = equipmentMaintScheduleId;
		this.name = name;
		this.editedBy = editedBy;
		this.modifiedOn = modifiedOn;
	}

	public long getEquipmentMaintScheduleId() {
		return equipmentMaintScheduleId;
	}

	public void setEquipmentMaintScheduleId(long equipmentMaintScheduleId) {
		this.equipmentMaintScheduleId = equipmentMaintScheduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(EquipmentMaintSchedule obj) {
		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName()).append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		return db.build().toString();
	}

}
