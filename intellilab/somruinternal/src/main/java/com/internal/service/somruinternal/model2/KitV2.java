package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

@Entity
@Table(name = "il_kit")
@PrimaryKeyJoinColumn(name = "kit_id")
public class KitV2 extends SalesItemV2 {

	@Column(nullable = true, length = 100)
	private String description;

	@Column(nullable = true, length = 100)
	private String client;

	@Column(nullable = true, length = 50)
	private String molecular;

	@Column(nullable = true, length = 50)
	private String biosimilar;

	@Column(nullable = true, length = 50)
	private String method;

	@Column(nullable = true, length = 50)
	private String status;

	@Column(nullable = true, length = 50)
	private String enteredBy;

	@Column(nullable = true)
	private Date enteredTime;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = true, length = 5000)
	private String comment;

	@OneToMany(mappedBy = "kit", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<KitComponentV2> kitComponents = new ArrayList<KitComponentV2>();

	@Column(nullable = true)
	private Long oldKitId;

	public KitV2() {
		super();
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
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the molecular
	 */
	public String getMolecular() {
		return molecular;
	}

	/**
	 * @param molecular the molecular to set
	 */
	public void setMolecular(String molecular) {
		this.molecular = molecular;
	}

	/**
	 * @return the biosimilar
	 */
	public String getBiosimilar() {
		return biosimilar;
	}

	/**
	 * @param biosimilar the biosimilar to set
	 */
	public void setBiosimilar(String biosimilar) {
		this.biosimilar = biosimilar;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the kitComponents
	 */
	public List<KitComponentV2> getKitComponents() {
		Collections.sort(kitComponents, new KitComponentV2Sorter());
		return kitComponents;
	}

	/**
	 * @param kitComponents the kitComponents to set
	 */
	public void setKitComponents(List<KitComponentV2> kitComponents) {
		this.kitComponents = kitComponents;
	}

	public void addKitComponent(KitComponentV2 kitComponent) {
		this.getKitComponents().add(kitComponent);
		kitComponent.setKit(this);
	}

	private boolean hasKitComponent(KitComponentV2 kitcomponentToCheck) {
		boolean found = false;
		if (kitcomponentToCheck != null) {
			for (KitComponentV2 kitcomponent : kitComponents) {
				if (kitcomponent.getKitComponentId() == kitcomponentToCheck.getKitComponentId()) {
					found = true;
				}
			}
		}
		return found;
	}

	private KitComponentV2 getkitComponent(long kitComponentId) {
		KitComponentV2 aKitComponent = null;
		for (KitComponentV2 sa : kitComponents) {
			if (sa.getKitComponentId() == kitComponentId) {
				aKitComponent = sa;
			}
		}
		return aKitComponent;
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
	 * @return the oldKitId
	 */
	public Long getOldKitId() {
		return oldKitId;
	}

	/**
	 * @param oldKitId the oldKitId to set
	 */
	public void setOldKitId(Long oldKitId) {
		this.oldKitId = oldKitId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(KitV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newKitComponentBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName())
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("active", this.isActive(), obj.isActive())
				.append("unitPrice", this.getUnitPrice(), obj.getUnitPrice())
				.append("editStatus", this.getEditStatus(), obj.getEditStatus())
				.append("reviewedBy", this.getReviewedBy(), obj.getReviewedBy())
				.append("editStatusComment", this.getEditStatusComment(), obj.getEditStatusComment())
				.append("editStatusTime", this.getEditStatusTime(), obj.getEditStatusTime())
				.append("packSize", this.getPackSize(), obj.getPackSize())
				.append("description", this.getDescription(), obj.getDescription())
				.append("client", this.getClient(), obj.getClient())
				.append("molecular", this.getMolecular(), obj.getMolecular())
				.append("biosimilar", this.getBiosimilar(), obj.getBiosimilar())
				.append("method", this.getMethod(), obj.getMethod()).append("status", this.getStatus(), obj.getStatus())
				.append("enteredBy", this.getEnteredBy(), obj.getEnteredBy())
				.append("enteredTime", this.getEnteredTime(), obj.getEnteredTime())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("comment", this.getComment(), obj.getComment())
				.append("oldKitId", this.getOldKitId(), obj.getOldKitId());

		int kitComponentIncrementer = 1;
		for (KitComponentV2 kitcomponent : kitComponents) {
			if (obj.hasKitComponent(kitcomponent)) {
				// check update diffs on same object
				KitComponentV2 otherKitComponent = obj.getkitComponent(kitcomponent.getKitComponentId());
				DiffResult kitcomponentDiffs = kitcomponent.diff(otherKitComponent);
				db.append(String.format("clientContact #%d (name:%s)", kitComponentIncrementer, kitcomponent.getName()),
						kitcomponentDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newKitComponentBuilder.append(System.lineSeparator());
				newKitComponentBuilder.append(String.format("kitComponent #%d is new - details : %s",
						kitComponentIncrementer, kitcomponent.toString()));
			}
			kitComponentIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newKitComponentBuilder.length() > 0) {
			diffStringBuilder.append(newKitComponentBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

}

class KitComponentV2Sorter implements Comparator<KitComponentV2> {
	// Used for sorting in ascending order of Kit Component catalog number
	@Override
	public int compare(KitComponentV2 a, KitComponentV2 b) {
		if (a.getCatalogNumber() != null && b.getCatalogNumber() != null)
			return a.getCatalogNumber().hashCode() - b.getCatalogNumber().hashCode();
		else if (a.getCatalogNumber() != null && b.getCatalogNumber() == null)
			return 1;
		else
			return -1;
	}

}
