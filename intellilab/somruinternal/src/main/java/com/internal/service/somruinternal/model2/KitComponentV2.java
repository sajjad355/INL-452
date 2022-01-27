package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "il_kit_component")
public class KitComponentV2 implements Diffable<KitComponentV2>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long kitComponentId;

	@Column(nullable = true, length = 100)
	private String name;

	@Column(nullable = false, length = 50)
	private String catalogNumber;

	@Column(nullable = false, length = 100)
	private String reagent;

	@Column(nullable = false, length = 50)
	private String amount;

	@Column(nullable = false, length = 50)
	private String unit;

	@Column(nullable = false, length = 150)
	private String packaging;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@ManyToOne(optional = false)
	@JoinColumn(name = "kit_id")
	@JsonBackReference
	private KitV2 kit;

	public KitComponentV2() {
		super();
	}

	/**
	 * @return the kitComponentId
	 */
	public long getKitComponentId() {
		return kitComponentId;
	}

	/**
	 * @param kitComponentId the kitComponentId to set
	 */
	public void setKitComponentId(long kitComponentId) {
		this.kitComponentId = kitComponentId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the component to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the catalogNumber
	 */
	public String getCatalogNumber() {
		return catalogNumber;
	}

	/**
	 * @param catalogNumber the catalogNumber to set
	 */
	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	/**
	 * @return the reagent
	 */
	public String getReagent() {
		return reagent;
	}

	/**
	 * @param reagent the reagent to set
	 */
	public void setReagent(String reagent) {
		this.reagent = reagent;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the packaging
	 */
	public String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging the packaging to set
	 */
	public void setPackaging(String packaging) {
		this.packaging = packaging;
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
	 * @return the kit
	 */
	public KitV2 getKit() {
		return kit;
	}

	/**
	 * @param kit the kit to set
	 */
	public void setKit(KitV2 kit) {
		this.kit = kit;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
    @Override
	public DiffResult diff(KitComponentV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("reagent", this.getReagent(), obj.getReagent())
				.append("amount", this.getAmount(), obj.getAmount()).append("unit", this.getUnit(), obj.getUnit())
				.append("packaging", this.getPackaging(), obj.getPackaging())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

	}

}
