package com.internal.service.somruinternal.model2;

import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "il_equipment_activity_log")
public class EquipmentActivityLogV2 implements Diffable<EquipmentActivityLogV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long equipmentActivityLogId;

	@Column(nullable = false, length = 255)
	private String activityPerformedBy;

	@Column(nullable = false)
	private Date activityDate;

	@Column(nullable = false, length = 255)
	private String activityType;

	@Column(nullable = false)
	private Date modifiedOn;

	public EquipmentActivityLogV2() {
		super();
	}

	/**
	 * @return the equipmentActivityLogId
	 */
	public long getEquipmentActivityLogId() {
		return equipmentActivityLogId;
	}

	/**
	 * @param equipmentActivityLogId the equipmentActivityLogId to set
	 */
	public void setEquipmentActivityLogId(long equipmentActivityLogId) {
		this.equipmentActivityLogId = equipmentActivityLogId;
	}

	/**
	 * @return the activityPerformedBy
	 */
	public String getActivityPerformedBy() {
		return activityPerformedBy;
	}

	/**
	 * @param activityPerformedBy the activityPerformedBy to set
	 */
	public void setActivityPerformedBy(String activityPerformedBy) {
		this.activityPerformedBy = activityPerformedBy;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the activityType
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

	@Override
	public DiffResult diff(EquipmentActivityLogV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("activityPerformedBy", this.getActivityPerformedBy(), obj.getActivityPerformedBy())
				.append("activityDate", this.getActivityDate(), obj.getActivityDate())
				.append("activityType", this.getActivityType(), obj.getActivityType())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

	}

}
