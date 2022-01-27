package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import net.bytebuddy.asm.Advice.This;

import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "il_quote")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteV2 implements Diffable<QuoteV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quoteId;

	@Column(nullable = false, length = 50)
	private String currency;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false)
	private Double currencyRate;

	@Column(nullable = false, length = 50)
	private String quoteNumber;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")

	private ClientCompanyV2 clientCompany;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "client_contact_id")
	private ClientContactV2 clientContact;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id")
	private CompanyShippingAddressV2 shippingAddress;

	@Column(nullable = false)
	private Date createdDate;

	@Column(nullable = false)
	private Date expirationDate;

	@Column(nullable = false, length = 50)
	private String paymentType;

	@Column(nullable = false)
	private long revision;

	@Column(nullable = false)
	private Date revisionDate;

	@Column(nullable = true)
	private Double shippingFee;

	@Column(nullable = true)
	private Double handlingFee;

	@Column(nullable = true)
	private double tax;

	@Column(nullable = true, length = 50)
	private String taxRate;

	@Column(nullable = false)
	private boolean complete;

	@Column(nullable = true, length = 5000)
	private String note;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = true)
	private Long oldQuoteId;

	@OneToMany(mappedBy = "quote", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<QuoteLineItemV2> lineItems = new ArrayList<QuoteLineItemV2>();

	public QuoteV2() {
		super();
	}

	/**
	 * @return the quoteId
	 */
	public long getQuoteId() {
		return quoteId;
	}

	/**
	 * @param quoteId the quoteId to set
	 */
	public void setQuoteId(long quoteId) {
		this.quoteId = quoteId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the currencyRate
	 */
	public Double getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * @param currencyRate the currencyRate to set
	 */
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * @return the quoteNumber
	 */
	public String getQuoteNumber() {
		return quoteNumber;
	}

	/**
	 * @param quoteNumber the quoteNumber to set
	 */
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	/**
	 * @return the clientCompany
	 */
	public ClientCompanyV2 getClientCompany() {
		return clientCompany;
	}

	/**
	 * @param clientCompany the clientCompany to set
	 */
	public void setClientCompany(ClientCompanyV2 clientCompany) {
		this.clientCompany = clientCompany;
	}

	/**
	 * @return the clientContact
	 */
	public ClientContactV2 getClientContact() {
		return clientContact;
	}

	/**
	 * @param clientContact the clientContact to set
	 */
	public void setClientContact(ClientContactV2 clientContact) {
		this.clientContact = clientContact;
	}

	/**
	 * @return the shippingAddress
	 */
	public CompanyShippingAddressV2 getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(CompanyShippingAddressV2 shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the revision
	 */
	public long getRevision() {
		return revision;
	}

	/**
	 * @param revision the revision to set
	 */
	public void setRevision(long revision) {
		this.revision = revision;
	}

	/**
	 * @return the revisionDate
	 */
	public Date getRevisionDate() {
		return revisionDate;
	}

	/**
	 * @param revisionDate the revisionDate to set
	 */
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	/**
	 * @return the shippingFee
	 */
	public Double getShippingFee() {
		return shippingFee;
	}

	/**
	 * @param shippingFee the shippingFee to set
	 */
	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}

	/**
	 * @return the handlingFee
	 */
	public Double getHandlingFee() {
		return handlingFee;
	}

	/**
	 * @param handlingFee the handlingFee to set
	 */
	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * @param complete the complete to set
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
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
	 * @return the lineItems
	 */
	public List<QuoteLineItemV2> getLineItems() {
		return lineItems;
	}

	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(List<QuoteLineItemV2> lineItems) {
		this.lineItems = lineItems;
	}

	/**
	 * @param lineItem the lineItem to add
	 */
	public void addLineItems(QuoteLineItemV2 lineItem) {
		this.lineItems.add(lineItem);
		lineItem.setQuote(this);
	}

	private boolean hasLineItem(QuoteLineItemV2 lineItemToCheck) {
		boolean found = false;
		if (lineItemToCheck != null) {
			for (QuoteLineItemV2 lineItem : lineItems) {
				if (lineItem.getQuoteLineItemId() == lineItemToCheck.getQuoteLineItemId()) {
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
	private QuoteLineItemV2 getLineItem(long lineItemId) {
		QuoteLineItemV2 aLineItem = null;
		for (QuoteLineItemV2 lineItem : lineItems) {
			if (lineItem.getQuoteLineItemId() == lineItemId) {
				aLineItem = lineItem;
			}
		}
		return aLineItem;
	}

	/**
	 * @return the taxRate
	 */
	public String getTaxRate() {
		return taxRate;
	}

	/**
	 * @param taxRate the taxRate to set
	 */
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
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

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("quoteId=");
//        sb.append(quoteId);
//        sb.append(",currency=");
//        sb.append(currency);
//        sb.append(",active=");
//        sb.append(active);
//        sb.append(",currencyRate=");
//        sb.append(currencyRate);
//        sb.append(",quoteNumber=");
//        sb.append(quoteNumber);
//        sb.append(",clientCompany=");
//        sb.append(clientCompany);
//        sb.append(",clientContact=");
//        sb.append(clientContact);
//        sb.append(",shippingAddress=");
//        sb.append(shippingAddress);
//        sb.append(",createdDate=");
//        sb.append(createdDate);
//        sb.append(",expirationDate=");
//        sb.append(expirationDate);
//        sb.append(",paymentType=");
//        sb.append(paymentType);
//        sb.append(",revision=");
//        sb.append(revision);
//        sb.append(",revisionDate=");
//        sb.append(revisionDate);
//        sb.append(",shippingFee=");
//        sb.append(shippingFee);
//        sb.append(",handlingFee=");
//        sb.append(handlingFee);
//        sb.append(",tax=");
//        sb.append(tax);
//        sb.append(",taxRate=");
//        sb.append(taxRate);
//        sb.append(",complete=");
//        sb.append(complete);
//        sb.append(",note=");
//        sb.append(note);
//        sb.append(",editedBy=");
//        sb.append(editedBy);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        if ( lineItems != null ) {
//            lineItems.forEach((lineItem) -> {
//                sb.append(lineItem.toString());
//                sb.append( "\n");
//            });    
//        }
//        sb.append("\n");
//        return sb.toString();
//    }

	/**
	 * @return the oldQuoteId
	 */
	public Long getOldQuoteId() {
		return oldQuoteId;
	}

	/**
	 * @param oldQuoteId the oldQuoteId to set
	 */
	public void setOldQuoteId(Long oldQuoteId) {
		this.oldQuoteId = oldQuoteId;
	}

	public boolean isExpired() {
		boolean expired = false;
		if (this.getExpirationDate() != null) {
			Date now = new Date(System.currentTimeMillis());
			if (now.compareTo(this.getExpirationDate()) > 0) {
				expired = true;
			}
		}
		return expired;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(QuoteV2 obj) {

		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newQuoteLineItemStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("currency", this.getCurrency(), obj.getCurrency())
				.append("active", this.isActive(), obj.isActive())
				.append("currencyRate", this.getCurrencyRate(), obj.getCurrencyRate())
				.append("quoteNumber", this.getQuoteNumber(), obj.getQuoteNumber())
				.append("createdDate", this.getCreatedDate(), obj.getCreatedDate())
				.append("expirationDate", this.getExpirationDate(), obj.getExpirationDate())
				.append("paymentType", this.getPaymentType(), obj.getPaymentType())
				.append("revision", this.getRevision(), obj.getRevision())
				.append("revisionDate", this.getRevisionDate(), obj.getRevisionDate())
				.append("shippingFee", this.getShippingFee(), obj.getShippingFee())
				.append("handlingFee", this.getHandlingFee(), obj.getHandlingFee())
				.append("tax", this.getTax(), obj.getTax()).append("taxRate", this.getTaxRate(), obj.getTaxRate())
				.append("complete", this.isComplete(), obj.isComplete()).append("note", this.getNote(), obj.getNote())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("oldQuoteId", this.getOldQuoteId(), obj.getOldQuoteId())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		DiffResult clientCompanyDiffs = this.clientCompany.diff(obj.getClientCompany());
		db.append("clientCompany", clientCompanyDiffs);

		DiffResult clientContactDiffs = this.clientContact.diff(obj.getClientContact());
		db.append("clientContact", clientContactDiffs);

		DiffResult shippingAddressDiffs = this.shippingAddress.diff(obj.getShippingAddress());
		db.append("shippingAddress", shippingAddressDiffs);

		// iterate over this object contacts and check contact collection from both
		// objects to see what has been added or updated
		// note that no delete check is necessary as we do not delete contacts, simply
		// set them to inactive
		int lineItemsIncrementer = 1;
		for (QuoteLineItemV2 lineItem : lineItems) {
			if (obj.hasLineItem(lineItem)) {
				// check update diffs on same object
				QuoteLineItemV2 otherLineItem = obj.getLineItem(lineItem.getQuoteLineItemId());
				DiffResult lineItemDiffs = lineItem.diff(otherLineItem);
				db.append(String.format("QuuoteLineItem #%d (name:%s)", lineItemsIncrementer, lineItem.getName()),
						lineItemDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newQuoteLineItemStringBuilder.append(System.lineSeparator());
				newQuoteLineItemStringBuilder.append(String.format("QuuoteLineItem #%d is new - details : %s",
						lineItemsIncrementer, lineItem.toString()));
			}
			lineItemsIncrementer++;
		}
		
		diffStringBuilder.append(db.build().toString());

		// join all the differences together

		if (newQuoteLineItemStringBuilder.length() > 0) {
			diffStringBuilder.append(newQuoteLineItemStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

	@Override
	public DiffResult diff(QuoteV2 obj) {

		StringBuilder diffStringBuilder = new StringBuilder();

		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("currency", this.getCurrency(), obj.getCurrency())
				.append("active", this.isActive(), obj.isActive())
				.append("currencyRate", this.getCurrency(), obj.getCurrency())
				.append("quoteNumber", this.getQuoteNumber(), obj.getQuoteNumber())
				.append("createdDate", this.getCreatedDate(), obj.getCreatedDate())
				.append("expirationDate", this.getExpirationDate(), obj.getExpirationDate())
				.append("paymentType", this.getPaymentType(), obj.getPaymentType())
				.append("revision", this.getRevision(), obj.getRevision())
				.append("revisionDate", this.getRevisionDate(), obj.getRevisionDate())
				.append("shippingFee", this.getShippingFee(), obj.getShippingFee())
				.append("handlingFee", this.getHandlingFee(), obj.getHandlingFee())
				.append("tax", this.getTax(), obj.getTax()).append("taxRate", this.getTaxRate(), obj.getTaxRate())
				.append("complete", this.isComplete(), obj.isComplete()).append("note", this.getNote(), obj.getNote())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("oldQuoteId", this.getOldQuoteId(), obj.getOldQuoteId())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn()).build();

//		DiffResult salesItemV2Diffs = this.salesItem.diff(obj.getSalesItem());
//		db.append("salesItem", salesItemV2Diffs);

	}

}
