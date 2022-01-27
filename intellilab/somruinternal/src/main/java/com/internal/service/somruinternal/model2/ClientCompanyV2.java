package com.internal.service.somruinternal.model2;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import org.apache.commons.lang3.builder.*;

@Entity
@Table(name = "il_client_company")
public class ClientCompanyV2 implements Diffable<ClientCompanyV2>{

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientCompanyV2.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long clientCompanyId;

	@Column(nullable = false, unique = true, length = 100)
	private String companyName;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "client_company_id")
	private List<CompanyShippingAddressV2> shippingAddresses = new ArrayList<CompanyShippingAddressV2>();

	@OneToOne(optional = false, fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id")
	private AddressV2 billingAddress;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "client_company_id")
	private List<ClientContactV2> clientContacts = new ArrayList<ClientContactV2>();

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	public ClientCompanyV2() {
		super();
	}

	/**
	 * @return the clientCompanyId
	 */
	public long getClientCompanyId() {
		return clientCompanyId;
	}

	/**
	 * @param clientCompanyId the clientCompanyId to set
	 */
	public void setClientCompanyId(long clientCompanyId) {
		this.clientCompanyId = clientCompanyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the billingAddress
	 */
	public AddressV2 getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(AddressV2 billingAddress) {
		this.billingAddress = billingAddress;
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
	 * @return the clientContacts
	 */
	public List<ClientContactV2> getClientContacts() {
		return clientContacts;
	}

	/**
	 * @param clientContacts the clientContacts to set
	 */
	public void setClientContacts(List<ClientContactV2> clientContacts) {
		this.clientContacts = clientContacts;
	}

	/**
	 * @param clientContact the clientContact to add
	 */
	public void addClientContact(ClientContactV2 clientContact) {
		this.clientContacts.add(clientContact);
	}

	/**
	 * @param clientContactToCheck the clientContactToCheck to check if present
	 * @return true if pass client contact is present for this client company record
	 *         (based on id) and false otherwise.
	 */
	private boolean hasClientContact(ClientContactV2 clientContactToCheck) {
		boolean found = false;
		if (clientContactToCheck != null) {
			for (ClientContactV2 contact : clientContacts) {
				if (contact.getClientContactId() == clientContactToCheck.getClientContactId()) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * @param clientContactId the clientContactId of the contact to return
	 * @return ClientContactV2 if the collection of contacts haa a record with this
	 *         id. If not found, return null
	 */
	private ClientContactV2 getClientContact(long clientContactId) {
		ClientContactV2 aContact = null;
		for (ClientContactV2 contact : clientContacts) {
			if (contact.getClientContactId() == clientContactId) {
				aContact = contact;
			}
		}
		return aContact;
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
	 * @return the shippingAddresses
	 */
	public List<CompanyShippingAddressV2> getShippingAddresses() {
		return shippingAddresses;
	}

	/**
	 * @param shippingAddresses the shippingAddresses to set
	 */
	public void setShippingAddresses(List<CompanyShippingAddressV2> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}

	/**
	 * @param shippingAddress the shippingAddress to add
	 */
	public void addShippingAddress(CompanyShippingAddressV2 shippingAddress) {
		this.shippingAddresses.add(shippingAddress);
	}

	/**
	 * @param shippingAddressToCheck the shippingAddressToCheck to check if present
	 * @return true if pass client shipping address is present for this client
	 *         company record (based on id) and false otherwise.
	 */
	private boolean hasShippingAddress(CompanyShippingAddressV2 shippingAddressToCheck) {
		boolean found = false;
		if (shippingAddressToCheck != null) {
			for (CompanyShippingAddressV2 shppingAddress : shippingAddresses) {
				if (shppingAddress.getAddressId() == shippingAddressToCheck.getAddressId()) {
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
	private CompanyShippingAddressV2 getShippingAddress(long addressId) {
		CompanyShippingAddressV2 aShippingAddress = null;
		for (CompanyShippingAddressV2 sa : shippingAddresses) {
			if (sa.getAddressId() == addressId) {
				aShippingAddress = sa;
			}
		}
		return aShippingAddress;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(ClientCompanyV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newContactStringBuilder = new StringBuilder();
		StringBuilder newShipAddressStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("companyName", this.getCompanyName(), obj.getCompanyName())
				.append("active", this.isActive(), obj.isActive())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		DiffResult billAddressDiffs = this.billingAddress.diff(obj.getBillingAddress());
		db.append("billingAddress", billAddressDiffs);

		// iterate over this object contacts and check contact collection from both
		// objects to see what has been added or updated
		// note that no delete check is necessary as we do not delete contacts, simply
		// set them to inactive
		int contactIncrementer = 1;
		for (ClientContactV2 contact : clientContacts) {
			if (obj.hasClientContact(contact)) {
				// check update diffs on same object
				ClientContactV2 otherContact = obj.getClientContact(contact.getClientContactId());
				DiffResult contactDiffs = contact.diff(otherContact);
				db.append(String.format("clientContact #%d (name:%s)", contactIncrementer, contact.getName()),
						contactDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newContactStringBuilder.append(System.lineSeparator());
				newContactStringBuilder.append(String.format("clientContact #%d is new - details : %s",
						contactIncrementer, contact.toString()));
			}
			contactIncrementer++;
		}

		int shipAddressIncrementer = 1;
		for (CompanyShippingAddressV2 shippingAddress : shippingAddresses) {
			if (obj.hasShippingAddress(shippingAddress)) {
				// check update diffs on same object
				CompanyShippingAddressV2 otherShipAddress = obj.getShippingAddress(shippingAddress.getAddressId());
				DiffResult shippingAddressDiffs = shippingAddress.diff(otherShipAddress);
				db.append(String.format("shippingAddresses #%d (name:%s)", shipAddressIncrementer,
						shippingAddress.getLocationName()), shippingAddressDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newShipAddressStringBuilder.append(System.lineSeparator());
				newShipAddressStringBuilder.append(String.format("shippingAddress #%d is new - details : %s",
						shipAddressIncrementer, shippingAddress.toString()));
			}
			shipAddressIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newContactStringBuilder.length() > 0) {
			diffStringBuilder.append(newContactStringBuilder.toString());
		}
		if (newShipAddressStringBuilder.length() > 0) {
			diffStringBuilder.append(newShipAddressStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

	@Override
	public DiffResult diff(ClientCompanyV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("companyName", this.getCompanyName(), obj.getCompanyName())
				.append("active", this.isActive(), obj.isActive())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

	}

}
