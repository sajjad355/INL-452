package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "il_sop_link")
public class SopLinkV2 implements Diffable<SopLinkV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private double requiredQuantity;

	@Column(nullable = false, length = 50)
	private String requiredUnit;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	// note that the referenced recipe has no link back to the referencing recipe
	// and so is defined without a back reference annotation as there is no danger
	// of a circular reference unlike the referencingRecipe side.
	@ManyToOne(optional = false)
	@JoinColumn(name = "referenced_sop_id")
	private SopV2 referencedSop;

	@ManyToOne(optional = false)
	@JoinColumn(name = "referencing_sop_id")
	@JsonBackReference
	private SopV2 referencingSop;

	public SopLinkV2() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the requiredQuantity
	 */
	public double getRequiredQuantity() {
		return requiredQuantity;
	}

	/**
	 * @param requiredQuantity the requiredQuantity to set
	 */
	public void setRequiredQuantity(double requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}

	/**
	 * @return the requiredUnit
	 */
	public String getRequiredUnit() {
		return requiredUnit;
	}

	/**
	 * @param requiredUnit the requiredUnit to set
	 */
	public void setRequiredUnit(String requiredUnit) {
		this.requiredUnit = requiredUnit;
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
	 * @return the referencedSOP
	 */
	public SopV2 getReferencedSop() {
		return referencedSop;
	}

	/**
	 * @param referencedSop the referencedSop to set
	 */
	public void setReferencedSop(SopV2 referencedSop) {
		this.referencedSop = referencedSop;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "id=");
//        sb.append( id );
//        sb.append( ",requiredQuantity=");
//        sb.append( requiredQuantity );
//        sb.append( ",requiredUnit=");
//        sb.append( requiredUnit );
//        sb.append( ",editedBy=");
//        sb.append( editedBy );
//        sb.append( editedBy );
//        sb.append( ",modifiedOn=");
//        sb.append( modifiedOn );
//        sb.append( ",referencedSop=");
//        sb.append( referencedSop );
//        
//        return sb.toString();
//    }

	/**
	 * @return the referencingSOP
	 */
	public SopV2 getReferencingSop() {
		return referencingSop;
	}

	/**
	 * @param referencingSop the referencingSop to set
	 */
	public void setReferencingSop(SopV2 referencingSop) {
		this.referencingSop = referencingSop;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(SopLinkV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("requiredQuantity", this.getRequiredQuantity(), obj.getRequiredQuantity())
				.append("requiredUnit", this.getRequiredUnit(), obj.getRequiredUnit())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("referencedSop", this.getReferencedSop(), obj.getReferencedSop())
				.append("referencingSop", this.getReferencingSop(), obj.getReferencingSop()).build();

	}

}
