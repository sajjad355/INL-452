package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "il_setting_value")
public class SettingValueV2 implements Diffable<SettingValueV2> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long settingValueId;

    @Column(nullable = false, length = 5000)
    private String value;

    @Column(nullable = true,length=50)
    private String attribute;

    @Column(nullable = true,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="setting_id")
    @JsonBackReference
    private SettingV2 setting;

    public SettingValueV2() {
        super();
    }

    /**
     * @return the settingValueId
     */
    public long getSettingValueId() {
        return settingValueId;
    }

    /**
     * @param settingValueId the settingValueId to set
     */
    public void setSettingValueId(long settingValueId) {
        this.settingValueId = settingValueId;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
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

    /*
     * @return String representation of object
     */
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "settingValueId=");
//        sb.append( settingValueId );
//        sb.append( ",value=");
//        sb.append( value );
//        sb.append( ",editedBy=");
//        sb.append( editedBy );
//        sb.append( ",modifiedOn=");
//        sb.append( modifiedOn );
//        sb.append( ",attribute=");
//        sb.append( attribute );
//        return sb.toString();
//    }

    /**
     * @return the setting
     */
    public SettingV2 getSetting() {
        return setting;
    }

    /**
     * @param setting the setting to set
     */
    public void setSetting(SettingV2 setting) {
        this.setting = setting;
    }

    /**
     * @return the attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

    
    @Override
	public DiffResult diff(SettingValueV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("value", this.getValue(), obj.getValue())
				.append("attribute", this.getAttribute(), obj.getAttribute())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

	}




}
