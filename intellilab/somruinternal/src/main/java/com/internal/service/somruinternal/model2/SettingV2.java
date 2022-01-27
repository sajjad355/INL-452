package com.internal.service.somruinternal.model2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "il_setting")
public class SettingV2 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long settingId;

	@Column(nullable = false, length = 100, unique = true)
	@NotBlank(message = "Setting name cannot be emptied")
	private String name;

	@Column(nullable = true, length = 100)
	private String description;

	@Column(nullable = true, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@OneToMany(mappedBy = "setting", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	@NotEmpty(message = "At least one setting value required")
	private List<SettingValueV2> settingValues = new ArrayList<SettingValueV2>();

	public SettingV2() {
		super();
	}

	/**
	 * @return the settingId
	 */
	public long getSettingId() {
		return settingId;
	}

	/**
	 * @param settingId the settingId to set
	 */
	public void setSettingId(long settingId) {
		this.settingId = settingId;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the settingValues
	 */
	public List<SettingValueV2> getSettingValues() {
		return settingValues;
	}

	/**
	 * @param settingValues the settingValues to set
	 */
	public void setSettingValues(List<SettingValueV2> settingValues) {
		this.settingValues = settingValues;
	}

	/**
	 * @param settingValue the settingValue to add
	 */
	public void addSettingValue(SettingValueV2 settingValue) {
		this.settingValues.add(settingValue);
		settingValue.setSetting(this);
	}

	/**
	 *
	 * @return String representation of object
	 */
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "settingId=");
//        sb.append( settingId );
//        sb.append( ",name=");
//        sb.append( name );
//        sb.append( ",description=");
//        sb.append( description );
//        sb.append( ",editedBy=");
//        sb.append( editedBy );
//        sb.append( ",modifiedOn=");
//        sb.append( modifiedOn );
//        sb.append( "\n");
//        settingValues.forEach((settingValue) -> {
//            sb.append(settingValue.toString());
//            sb.append( "\n");
//        });
//        return sb.toString();
//    }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	private boolean hasSettingValue(SettingValueV2 settingToCheck) {
		boolean found = false;
		if (settingToCheck != null) {
			for (SettingValueV2 settingvalue : settingValues) {
				if (settingvalue.getSettingValueId() == settingToCheck.getSettingValueId()) {
					found = true;
				}
			}
		}
		return found;
	}

	private SettingValueV2 getsettingvalueDif(long seetingvalueId) {
		SettingValueV2 aSettingValue = null;
		for (SettingValueV2 sa : settingValues) {
			if (sa.getSettingValueId() == seetingvalueId) {
				aSettingValue = sa;
			}
		}
		return aSettingValue;
	}

	public String diffCompare(SettingV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newSettingValuesStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("description", this.getDescription(), obj.getDescription())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		int settingValuesIncrementer = 1;
		for (SettingValueV2 settingvalue : settingValues) {
			if (obj.hasSettingValue(settingvalue)) {
				// check update diffs on same object
				SettingValueV2 otherSettingValue = obj.getsettingvalueDif(settingvalue.getSettingValueId());
				DiffResult settingValueDiffs = settingvalue.diff(otherSettingValue);
				db.append(
						String.format("settingvalue #%d (name:%s)", settingValuesIncrementer, settingvalue.getValue()),
						settingValueDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newSettingValuesStringBuilder.append(System.lineSeparator());
				newSettingValuesStringBuilder.append(String.format("settingvalue #%d is new - details : %s",
						settingValuesIncrementer, settingvalue.toString()));
			}
			settingValuesIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newSettingValuesStringBuilder.length() > 0) {
			diffStringBuilder.append(newSettingValuesStringBuilder.toString());
		}

		return diffStringBuilder.toString();
	}

}
