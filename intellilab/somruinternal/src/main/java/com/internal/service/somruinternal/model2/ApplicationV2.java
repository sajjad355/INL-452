package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Entity
@Table(name = "il_application")
public class ApplicationV2 implements Diffable<ApplicationV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long applicationId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id")
	@JsonBackReference
	private ProductV2 product;

	@Column(nullable = true)
	private String purpose;

	@Column(nullable = true, length = 50)
	private String recommendedConcentration;

	@Column(nullable = true)
	private String comment;

	@Column(nullable = true, length = 5000)
	private String note;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	public ApplicationV2() {
		super();
	}

	/**
	 * @return the dbid
	 */
	public long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the dbid to set
	 */
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the product
	 */
	public ProductV2 getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(ProductV2 product) {
		this.product = product;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the recommendedConcentration
	 */
	public String getRecommendedConcentration() {
		return recommendedConcentration;
	}

	/**
	 * @param recommendedConcentration the recommendedConcentration to set
	 */
	public void setRecommendedConcentration(String recommendedConcentration) {
		this.recommendedConcentration = recommendedConcentration;
	}

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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * @return the modifyBy
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

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("applicationId=");
//        sb.append(applicationId);
//        sb.append(",purpose=");
//        sb.append(purpose);
//        sb.append(",recommendedConcentration=");
//        sb.append(recommendedConcentration);
//        sb.append(",comment=");
//        sb.append(comment);
//        sb.append(",note=");
//        sb.append(note);
//        sb.append(",editedBy=");
//        sb.append(editedBy);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        sb.append("\n");
//        return sb.toString();
//    }

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
    @Override
	public DiffResult diff(ApplicationV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("purpose", this.getPurpose(), obj.getPurpose())
				.append("recommendedConcentration", this.getRecommendedConcentration(),
						obj.getRecommendedConcentration())
				.append("comment", this.getComment(), obj.getComment()).append("note", this.getNote(), obj.getNote())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

	}

}
