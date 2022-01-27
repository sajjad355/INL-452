package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "il_product")
@PrimaryKeyJoinColumn(name = "product_id")
public class ProductV2 extends SalesItemV2 {

	@Column(nullable = true, length = 50)
	private String type;

	@Column(nullable = true, length = 100)
	private String description;

	@Column(nullable = true, length = 50)
	private String unit;

	@Column(nullable = true, length = 50)
	private String clonality;

	@Column(nullable = true, length = 50)
	private String host;

	@Column(nullable = true, length = 50)
	private String isotype;

	@Column(nullable = true, length = 50)
	private String immunogen;

	@Column(nullable = true, length = 100)
	private String purification;

	@Column(nullable = true, length = 50)
	private String buffer;

	@Column(nullable = true, length = 50)
	private String specificity;

	@Column(nullable = true, length = 50)
	private String reconstitution;

	@Column(nullable = true, length = 2000)
	private String storage;

	@Column(nullable = true)
	private double unitSize;

	@Column(nullable = true, length = 2000)
	private String comment;

	@Column(nullable = true, length = 50)
	private String enteredBy;

	@Column(nullable = true)
	private Date enteredTime;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = true)
	private Long oldProductId;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ApplicationV2> applications = new ArrayList<ApplicationV2>();

	public ProductV2() {
		super();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the clonality
	 */
	public String getClonality() {
		return clonality;
	}

	/**
	 * @param clonality the clonality to set
	 */
	public void setClonality(String clonality) {
		this.clonality = clonality;
	}

	/**
	 * @return the isotype
	 */
	public String getIsotype() {
		return isotype;
	}

	/**
	 * @param isotype the isotype to set
	 */
	public void setIsotype(String isotype) {
		this.isotype = isotype;
	}

	/**
	 * @return the immunogen
	 */
	public String getImmunogen() {
		return immunogen;
	}

	/**
	 * @param immunogen the immunogen to set
	 */
	public void setImmunogen(String immunogen) {
		this.immunogen = immunogen;
	}

	/**
	 * @return the purification
	 */
	public String getPurification() {
		return purification;
	}

	/**
	 * @param purification the purification to set
	 */
	public void setPurification(String purification) {
		this.purification = purification;
	}

	/**
	 * @return the buffer
	 */
	public String getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	/**
	 * @return the specificity
	 */
	public String getSpecificity() {
		return specificity;
	}

	/**
	 * @param specificity the specificity to set
	 */
	public void setSpecificity(String specificity) {
		this.specificity = specificity;
	}

	/**
	 * @return the reconstitution
	 */
	public String getReconstitution() {
		return reconstitution;
	}

	/**
	 * @param reconstitution the reconstitution to set
	 */
	public void setReconstitution(String reconstitution) {
		this.reconstitution = reconstitution;
	}

	/**
	 * @return the storage
	 */
	public String getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(String storage) {
		this.storage = storage;
	}

	/**
	 * @return the unitSize
	 */
	public double getUnitSize() {
		return unitSize;
	}

	/**
	 * @param unitSize the unitSize to set
	 */
	public void setUnitSize(double unitSize) {
		this.unitSize = unitSize;
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
	 * @return the enteredBy
	 */
	public String getEnteredBy() {
		return enteredBy;
	}

	/**
	 * @param enteredBy the enteredBy to set
	 */
	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	/**
	 * @return the enteredTime
	 */
	public Date getEnteredTime() {
		return enteredTime;
	}

	/**
	 * @param enteredTime the enteredTime to set
	 */
	public void setEnteredTime(Date enteredTime) {
		this.enteredTime = enteredTime;
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
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the applications
	 */
	public List<ApplicationV2> getApplications() {
		return applications;
	}

	/**
	 * @param applications the applications to set
	 */
	public void setApplications(List<ApplicationV2> applications) {
		this.applications = applications;
	}

	/**
	 * @param application the application to add
	 */
	public void addApplications(ApplicationV2 application) {
		this.applications.add(application);
		application.setProduct(this);
	}

	private boolean hasApplication(ApplicationV2 applicationtToCheck) {
		boolean found = false;
		if (applicationtToCheck != null) {
			for (ApplicationV2 application : applications) {
				if (application.getApplicationId() == applicationtToCheck.getApplicationId()) {
					found = true;
				}
			}
		}
		return found;
	}

	private ApplicationV2 getApplication(long applicationId) {
		ApplicationV2 aApplication = null;
		for (ApplicationV2 sa : applications) {
			if (sa.getApplicationId() == applicationId) {
				aApplication = sa;
			}
		}
		return aApplication;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("salesItemId=");
//        sb.append(super.getSalesItemId());
//        sb.append(",name=");
//        sb.append(super.getName());
//        sb.append(",catalogNumber=");
//        sb.append(super.getCatalogNumber());
//        sb.append(",active=");
//        sb.append(super.isActive());
//        sb.append(",description=");
//        sb.append(description);
//        sb.append(",host=");
//        sb.append(host);
//        sb.append(",storage=");
//        sb.append(storage);
//        sb.append(",reconstitution=");
//        sb.append(reconstitution);
//        sb.append(",specificity=");
//        sb.append(specificity);
//        sb.append(",buffer=");
//        sb.append(buffer);
//        sb.append(",purification=");
//        sb.append(purification);
//        sb.append(",immunogen=");
//        sb.append(immunogen);
//        sb.append(",isotype=");
//        sb.append(isotype);
//        sb.append(",clonality=");
//        sb.append(clonality);
//        sb.append(",unit=");
//        sb.append(unit);
//        sb.append(",type=");
//        sb.append(type);
//        sb.append(",editedBy=");
//        sb.append(editedBy);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        sb.append(",enteredBy=");
//        sb.append(enteredBy);
//        sb.append(",comment=");
//        sb.append(comment);
//        sb.append(",unitPrice=");
//        sb.append(super.getUnitPrice());
//        sb.append(",unitSize=");
//        sb.append(unitSize);
//        sb.append(",enteredTime=");
//        sb.append(enteredTime);
//        if ( applications != null ) {
//            applications.forEach((application) -> {
//                sb.append(application.toString());
//                sb.append( "\n");
//            });
//        }
//        sb.append("\n");
//        return sb.toString();
//    }

	/**
	 * @return the oldProductId
	 */
	public Long getOldProductId() {
		return oldProductId;
	}

	/**
	 * @param oldProductId the oldProductId to set
	 */
	public void setOldProductId(Long oldProductId) {
		this.oldProductId = oldProductId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(ProductV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newApplicationBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("active", this.isActive(), obj.isActive())
				.append("unitPrice", this.getUnitPrice(), obj.getUnitPrice())
				.append("editStatus", this.getEditStatus(), obj.getEditStatus())
				.append("reviewedBy", this.getReviewedBy(), obj.getReviewedBy())
				.append("editStatusComment", this.getEditStatusComment(), obj.getEditStatusComment())
				.append("editStatusTime", this.getEditStatusTime(), obj.getEditStatusTime())
				.append("packSize", this.getPackSize(), obj.getPackSize()).append("type", this.getType(), obj.getType())
				.append("description", this.getDescription(), obj.getDescription())
				.append("unit", this.getUnit(), obj.getUnit())
				.append("clonality", this.getClonality(), obj.getClonality())
				.append("host", this.getHost(), obj.getHost()).append("isotype", this.getIsotype(), obj.getIsotype())
				.append("immunogen", this.getImmunogen(), obj.getImmunogen())
				.append("purification", this.getPurification(), obj.getPurification())
				.append("buffer", this.getBuffer(), obj.getBuffer())
				.append("specificity", this.getSpecificity(), obj.getSpecificity())
				.append("reconstitution", this.getReconstitution(), obj.getReconstitution())
				.append("storage", this.getStorage(), obj.getStorage())
				.append("unitSize", this.getUnitSize(), obj.getUnitSize())
				.append("comment", this.getComment(), obj.getComment())
				.append("enteredBy", this.getEnteredBy(), obj.getEnteredBy())
				.append("enteredTime", this.getEnteredTime(), obj.getEnteredTime())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("oldProductId", this.getOldProductId(), obj.getOldProductId());

		int ApplicationIncrementer = 1;
		for (ApplicationV2 application : applications) {
			if (obj.hasApplication(application)) {
				// check update diffs on same object
				ApplicationV2 otherApplication = obj.getApplication(application.getApplicationId());
				DiffResult applicationDiffs = application.diff(otherApplication);
				db.append(String.format("application #%d (name:%s)", ApplicationIncrementer, application.getEditedBy()),
						applicationDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newApplicationBuilder.append(System.lineSeparator());
				newApplicationBuilder.append(String.format("Application #%d is new - details : %s",
						ApplicationIncrementer, application.toString()));
			}
			ApplicationIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newApplicationBuilder.length() > 0) {
			diffStringBuilder.append(newApplicationBuilder.toString());
		}
		if (newApplicationBuilder.length() > 0) {
			diffStringBuilder.append(newApplicationBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

}
