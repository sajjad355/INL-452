package com.internal.service.somruinternal.model2;

import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.bytebuddy.asm.Advice.This;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "il_equipment")
public class EquipmentV2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long equipmentId;

	@Column(nullable = false)
	private String equipmentIdentifier;

	@Column(nullable = false)
	private String serial;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String manufacturer;

	@Column(nullable = true)
	private String model;

	@Column(nullable = true, length = 5000)
	private String comment;

	@Column(nullable = false)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = true)
	private Date calibrationPerformed;

	@Column(nullable = false)
	private String calibrationScheduleInterval; // (weekly, monthly, yearly),

	@Column(nullable = false)
	private String maintenanceInterval; // (Daily, Monthly, weekly, yearly),

	private Date lastMaintenancePerformedDate;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	@Column(nullable = false)
	private String locationBuilding;

	@Column(nullable = true)
	private String locationUnit;

	@Column(nullable = true)
	private String locationRoom;

	@Column(nullable = true, length = 5000)
	private String nonRoutineMaintenanceComment;

	@Transient
	private boolean isCalibrationDue;

	@Transient
	private boolean isMaintanenceDue;

	@Column(nullable = false)
	private boolean active;

	@Transient
	private boolean isQualified;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "equipment_id")
	private List<EquipmentActivityLogV2> equipmentActivityLogs = new ArrayList<EquipmentActivityLogV2>();

	public EquipmentV2() {
		super();
	}

	/**
	 * @return the equipmentId
	 */
	public Long getEquipmentId() {
		return equipmentId;
	}

	/**
	 * @param equipmentId the equipmentId to set
	 */
	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	/**
	 * @return the id
	 */
	public String getEquipmentIdentifier() {
		return equipmentIdentifier;
	}

	/**
	 * @param id the id to set
	 */
	public void setEquipmentIdentifier(String equipmentIdentifier) {
		this.equipmentIdentifier = equipmentIdentifier;
	}

	/**
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the manufacture
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacture the manufacture to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the location
	 */
//    public LocationV2 getLocation() {
//        return location;
//    }
//
//    /**
//     * @param location the location to set
//     */
//    public void setLocation(LocationV2 location) {
//        this.location = location;
//    }
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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

	public Date getCalibrationPerformed() {
		return calibrationPerformed;
	}

	public void setCalibrationPerformed(Date calibrationPerformed) {
		this.calibrationPerformed = calibrationPerformed;
	}

	public String getCalibrationScheduleInterval() {
		return calibrationScheduleInterval;
	}

	public void setCalibrationScheduleInterval(String calibrationScheduleInterval) {
		this.calibrationScheduleInterval = calibrationScheduleInterval;
	}

	public String getMaintenanceInterval() {
		return maintenanceInterval;
	}

	public void setMaintenanceInterval(String maintenanceInterval) {
		this.maintenanceInterval = maintenanceInterval;
	}

	public Date getLastMaintenancePerformedDate() {
		return lastMaintenancePerformedDate;
	}

	public void setLastMaintenancePerformedDate(Date lastMaintenancePerformedDate) {
		this.lastMaintenancePerformedDate = lastMaintenancePerformedDate;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getLocationBuilding() {
		return locationBuilding;
	}

	public void setLocationBuilding(String locationBuilding) {
		this.locationBuilding = locationBuilding;
	}

	public String getLocationUnit() {
		return locationUnit;
	}

	public void setLocationUnit(String locationUnit) {
		this.locationUnit = locationUnit;
	}

	public String getLocationRoom() {
		return locationRoom;
	}

	public void setLocationRoom(String locationRoom) {
		this.locationRoom = locationRoom;
	}

	public String getNonRoutineMaintenanceComment() {
		return nonRoutineMaintenanceComment;
	}

	public void setNonRoutineMaintenanceComment(String nonRoutineMaintenanceComment) {
		this.nonRoutineMaintenanceComment = nonRoutineMaintenanceComment;
	}

	// Methods for new properties
	public boolean isCalibrationDue() {
		return isCalibrationDue;
	}

	public void setCalibrationDue(boolean isCalibrationDue) {
		this.isCalibrationDue = isCalibrationDue;
	}

	public boolean isMaintanenceDue() {
		return isMaintanenceDue;
	}

	public void setMaintanenceDue(boolean isMaintanenceDue) {
		this.isMaintanenceDue = isMaintanenceDue;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isQualified() {
		return isQualified;
	}

	public void setQualified(boolean isQualified) {
		this.isQualified = isQualified;
	}

	/*
	 * @Override public String toString() { return "equipmentId=" + equipmentId +
	 * ", equipmentIdentifier=" + equipmentIdentifier + ", serial=" + serial +
	 * ", name=" + name + ", manufacturer=" + manufacturer + ", model=" + model +
	 * ", comment=" + comment + ", editedBy=" + editedBy + ", modifiedOn=" +
	 * modifiedOn + ", calibrationPerformed=" + calibrationPerformed +
	 * ", calibrationScheduleInterval=" + calibrationScheduleInterval +
	 * ", maintenanceInterval=" + maintenanceInterval +
	 * ", lastMaintenancePerformedDate=" + lastMaintenancePerformedDate + ", image="
	 * + Arrays.toString(image) + ", locationBuilding=" + locationBuilding +
	 * ", locationUnit=" + locationUnit + ", locationRoom=" + locationRoom +
	 * ", nonRoutineMaintenanceComment=" + nonRoutineMaintenanceComment +
	 * ", nonRoutineMaintenanceAttachment=" + ", isMaintanenceDue=" +
	 * isMaintanenceDue + ", active=" + active + "\n"; }
	 */
	@PostLoad
	@PostUpdate
	@PostPersist
	public void setMaintenanceCalibrationQualifiedStatus() {
		this.isMaintanenceDue = updateDueStatus(this.lastMaintenancePerformedDate, this.maintenanceInterval, 0);
		this.isCalibrationDue = updateDueStatus(this.calibrationPerformed, this.calibrationScheduleInterval, 0);
		this.isQualified = !(updateDueStatus(this.lastMaintenancePerformedDate, this.maintenanceInterval, 7)
				|| updateDueStatus(this.calibrationPerformed, this.calibrationScheduleInterval, 7));
	}

	public boolean updateDueStatus(Date lasPerformDate, String intervalString, int bufferDays) {
		if (lasPerformDate == null || intervalString == null) {
			return false;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(lasPerformDate);

		if (intervalString.equalsIgnoreCase("daily")) {
			cal.add(Calendar.DATE, (1 + bufferDays));
		} else if (intervalString.equalsIgnoreCase("weekly")) {
			cal.add(Calendar.DATE, (7 + bufferDays));
		} else if (intervalString.equalsIgnoreCase("monthly")) {
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, bufferDays);
		} else if (intervalString.equalsIgnoreCase("yearly")) {
			cal.add(Calendar.YEAR, 1);
			cal.add(Calendar.DATE, bufferDays);
		} else {
			return false;
		}

		Date currentDate = new Date();

		if (currentDate.after(cal.getTime())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the equipmentActivityLogs
	 */
	public List<EquipmentActivityLogV2> getEquipmentActivityLogs() {
		return equipmentActivityLogs;
	}

	/**
	 * @param equipmentActivityLogs the equipmentActivityLogs to set
	 */
	public void setEquipmentActivityLogs(List<EquipmentActivityLogV2> equipmentActivityLogs) {
		this.equipmentActivityLogs = equipmentActivityLogs;
	}

	private boolean hasequipmentActivityLogs(EquipmentActivityLogV2 equipmentActivityLogCheck) {
		boolean found = false;
		if (equipmentActivityLogCheck != null) {
			for (EquipmentActivityLogV2 activitylog : equipmentActivityLogs) {
				if (activitylog.getEquipmentActivityLogId() == equipmentActivityLogCheck.getEquipmentActivityLogId()) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * @param addressId the addressId of the address to return
	 * @return CompanyShippingAddressV2 if the collection of shipping addresses has
	 *         a record with this id. If not found, return null
	 */
	private EquipmentActivityLogV2 getactivitylog(long equipmentActivityLogId) {
		EquipmentActivityLogV2 activitylog = null;
		for (EquipmentActivityLogV2 sa : equipmentActivityLogs) {
			if (sa.getEquipmentActivityLogId() == equipmentActivityLogId) {
				activitylog = sa;
			}
		}
		return activitylog;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(EquipmentV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newActivityLogStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("equipmentIdentifier", this.getEquipmentIdentifier(), obj.getEquipmentIdentifier())
				.append("serial", this.getSerial(), obj.getSerial()).append("name", this.getName(), obj.getName())
				.append("manufacturer", this.getManufacturer(), obj.getManufacturer())
				.append("model", this.getModel(), obj.getModel()).append("comment", this.getComment(), obj.getComment())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("calibrationPerformed", this.getCalibrationPerformed(), obj.getCalibrationPerformed())
				.append("calibrationScheduleInterval", this.getCalibrationScheduleInterval(),
						obj.getCalibrationScheduleInterval())
				.append("maintenanceInterval", this.getMaintenanceInterval(), obj.getMaintenanceInterval())
				.append("lastMaintenancePerformedDate", this.getLastMaintenancePerformedDate(),
						obj.getLastMaintenancePerformedDate())
				.append("image", this.getImage(), obj.getImage())
				.append("locationBuilding", this.getLocationBuilding(), obj.getLocationBuilding())
				.append("locationUnit", this.getLocationUnit(), obj.getLocationUnit())
				.append("locationRoom", this.getLocationRoom(), obj.getLocationRoom())
				.append("nonRoutineMaintenanceComment", this.getNonRoutineMaintenanceComment(),
						obj.getNonRoutineMaintenanceComment())
				.append("isCalibrationDue", this.isCalibrationDue(), obj.isCalibrationDue())
				.append("isMaintanenceDue", this.isMaintanenceDue(), obj.isMaintanenceDue())
				.append("active", this.isActive(), obj.isActive())
				.append("isQualified", this.isQualified(), obj.isQualified());

		diffStringBuilder.append(db.build().toString());

		return diffStringBuilder.toString();
	}
}
