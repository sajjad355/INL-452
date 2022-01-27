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

@Entity
@Table(name = "il_sop")
public class SopV2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sopId;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false, length = 50)
	private String sopIdentifier;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = true, length = 50)
	private String volume;

	@Column(nullable = true, length = 50)
	private String unit;

	@Column(nullable = true, length = 100)
	private String description;

	@Column(nullable = true, length = 5000)
	private String direction;

	@Column(nullable = true, length = 50)
	private String enteredBy;

	@Column(nullable = true)
	private Date enteredTime;

	@Column(nullable = true, length = 50)
	private String reviewedBy;

	@Column(nullable = false, length = 50)
	private String editStatus;

	@Column(nullable = true, length = 5000)
	private String editStatusComment;

	@Column(nullable = true)
	private Date editStatusTime;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@OneToMany(mappedBy = "sop", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SopComponent> components = new ArrayList<SopComponent>();

	@OneToMany(mappedBy = "referencingSop", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SopLinkV2> sopLinks = new ArrayList<SopLinkV2>();

	public SopV2() {
		super();
	}

	/**
	 * @return the sopId
	 */
	public long getSopId() {
		return sopId;
	}

	/**
	 * @param sopId the sopId to set
	 */
	public void setSopId(long sopId) {
		this.sopId = sopId;
	}

	/**
	 * @return the sopIdentifier
	 */
	public String getSopIdentifier() {
		return sopIdentifier;
	}

	/**
	 * @param sopIdentifier the sopIdentifier to set
	 */
	public void setSopIdentifier(String sopIdentifier) {
		this.sopIdentifier = sopIdentifier;
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
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
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
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
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
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
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
	 * @return the editStatus
	 */
	public String getEditStatus() {
		return editStatus;
	}

	/**
	 * @param editStatus the editStatus to set
	 */
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the editStatusComment
	 */
	public String getEditStatusComment() {
		return editStatusComment;
	}

	/**
	 * @param editStatusComment the editStatusComment to set
	 */
	public void setEditStatusComment(String editStatusComment) {
		this.editStatusComment = editStatusComment;
	}

	/**
	 * @return the editStatusTime
	 */
	public Date getEditStatusTime() {
		return editStatusTime;
	}

	/**
	 * @param editStatusTime the editStatusTime to set
	 */
	public void setEditStatusTime(Date editStatusTime) {
		this.editStatusTime = editStatusTime;
	}

	/**
	 * @return the components
	 */
	public List<SopComponent> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(List<SopComponent> components) {
		this.components = components;
	}

	/**
	 * @return the sopLinks
	 */
	public List<SopLinkV2> getSopLinks() {
		return sopLinks;
	}

	/**
	 * @param sopLinks the recipeLinks to set
	 */
	public void setSopLinks(List<SopLinkV2> sopLinks) {
		this.sopLinks = sopLinks;
	}

	/**
	 * @param sopLink the sopLink to add
	 */
	public void addSopLink(SopLinkV2 sopLink) {
		this.sopLinks.add(sopLink);
		sopLink.setReferencingSop(this);
	}

	private boolean hasSopLink(SopLinkV2 sopLinkToCheck) {
		boolean found = false;
		if (sopLinkToCheck != null) {
			for (SopLinkV2 soplink : sopLinks) {
				if (soplink.getId() == sopLinkToCheck.getId()) {
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
	private SopLinkV2 getSopLink(long Id) {
		SopLinkV2 aSopLink = null;
		for (SopLinkV2 sa : sopLinks) {
			if (sa.getId() == Id) {
				aSopLink = sa;
			}
		}
		return aSopLink;
	}

	/**
	 * @param component the component to add
	 */
	public void addComponent(SopComponent component) {
		this.components.add(component);
		component.setSop(this);
	}

	private boolean hasComponent(SopComponent sopComponentToCheck) {
		boolean found = false;
		if (sopComponentToCheck != null) {
			for (SopComponent component : components) {
				if (component.getComponentId() == sopComponentToCheck.getComponentId()) {
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
	private SopComponent getComponent(long componentId) {
		SopComponent aSopComponent = null;
		for (SopComponent sa : components) {
			if (sa.getComponentId() == componentId) {
				aSopComponent = sa;
			}
		}
		return aSopComponent;
	}

//    
//    
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append( "sopId=");
//        sb.append( sopId );
//        sb.append( ",active=");
//        sb.append( active );
//        sb.append( ",sopIdentifier=");
//        sb.append( sopIdentifier );
//        sb.append( ",name=");
//        sb.append( name );
//        sb.append( ",volume=");
//        sb.append( volume );
//        sb.append( ",unit=");
//        sb.append( unit );
//        sb.append( ",description=");
//        sb.append( description );
//        sb.append( ",direction=");
//        sb.append( direction );
//        sb.append( ",enteredBy=");
//        sb.append( enteredBy );
//        sb.append( ",enteredTime=");
//        sb.append( enteredTime );
//        sb.append( ",reviewedBy=");
//        sb.append( reviewedBy );
//        sb.append( ",editStatus=");
//        sb.append( editStatus );
//        sb.append( ",editStatusComment=");
//        sb.append( editStatusComment );
//        sb.append( ",editStatusTime=");
//        sb.append( editStatusTime );
//        sb.append( ",modifiedOn=");
//        sb.append( modifiedOn );
//        sb.append( ",editedBy=");
//        sb.append( editedBy );
//        if ( components != null ) {
//            components.forEach((component) -> {
//                sb.append(component.toString());
//                sb.append( "\n");
//            });    
//        }
//        if ( sopLinks != null ) {
//            sopLinks.forEach((sopLink) -> {
//                sb.append(sopLink.toString());
//                sb.append( "\n");
//            });   
//        }   
//                
//        return sb.toString();
//    }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(SopV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder componentStringBuilder = new StringBuilder();
		StringBuilder sopLinkStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("active", this.isActive(), obj.isActive())
				.append("sopIdentifier", this.getSopIdentifier(), obj.getSopIdentifier())
				.append("name", this.getName(), obj.getName()).append("volume", this.getVolume(), obj.getVolume())
				.append("unit", this.getUnit(), obj.getUnit())
				.append("description", this.getDescription(), obj.getDescription())
				.append("direction", this.getDirection(), obj.getDirection())
				.append("enteredBy", this.getEnteredBy(), obj.getEnteredBy())
				.append("enteredTime", this.getEnteredTime(), obj.getEnteredTime())
				.append("reviewedBy", this.getReviewedBy(), obj.getReviewedBy())
				.append("editStatus", this.getEditStatus(), obj.getEditStatus())
				.append("editStatusComment", this.getEditStatusComment(), obj.getEditStatusComment())
				.append("editStatusTime", this.getEditStatusTime(), obj.getEditStatusTime())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		// iterate over this object contacts and check contact collection from both
		// objects to see what has been added or updated
		// note that no delete check is necessary as we do not delete contacts, simply
		// set them to inactive
		int componentIncrementer = 1;
		for (SopComponent component : components) {
			if (obj.hasComponent(component)) {
				// check update diffs on same object
				SopComponent otherComponent = obj.getComponent(component.getComponentId());
				DiffResult componentDiffs = component.diff(otherComponent);
				db.append(String.format("component #%d (name:%s)", componentIncrementer, component.getItemName()),
						componentDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				componentStringBuilder.append(System.lineSeparator());
				componentStringBuilder.append(String.format("component #%d is new - details : %s", componentIncrementer,
						component.toString()));
			}
			componentIncrementer++;
		}

		int sopLinkIncrementer = 1;
		for (SopLinkV2 sopLink : sopLinks) {
			if (obj.hasSopLink(sopLink)) {
				// check update diffs on same object
				SopLinkV2 otherSopLink = obj.getSopLink(sopLink.getId());
				DiffResult soplinkDiffs = sopLink.diff(otherSopLink);
				db.append(String.format("SopLinks #%d (name:%s)", sopLinkIncrementer, sopLink.getEditedBy()),
						soplinkDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				sopLinkStringBuilder.append(System.lineSeparator());
				sopLinkStringBuilder.append(String.format("sopLinkStringBuilder #%d is new - details : %s",
						sopLinkIncrementer, sopLink.toString()));
			}
			sopLinkIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (componentStringBuilder.length() > 0) {
			diffStringBuilder.append(componentStringBuilder.toString());
		}
		if (sopLinkStringBuilder.length() > 0) {
			diffStringBuilder.append(sopLinkStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

}
