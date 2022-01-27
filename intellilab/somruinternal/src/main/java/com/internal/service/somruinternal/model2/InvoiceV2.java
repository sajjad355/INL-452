package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "il_invoice")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceV2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long invoiceId;

	@Column(nullable = true, length = 100)
	private String shippingAttention;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false, length = 50)
	private String currency;

	@Column(nullable = false)
	private double currencyRate;

	@Column(nullable = false, length = 50)
	private String invoiceNumber;

	@Column(nullable = false)
	private Date dateCreated;

	@Column(nullable = false)
	private long revision;

	@Column(nullable = false)
	private Date revisionDate;

	@Column(nullable = true, length = 50)
	private String billingAttention;

	@Column(nullable = true, length = 50)
	private String purchaseOrderNumber;

	@Column(nullable = true, length = 50)
	private String courier;

	@Column(nullable = false, length = 50)
	private String paymentType;

	@Column(nullable = true, length = 50)
	private String shippingTerm;

	@Column(nullable = true)
	private Double shippingFee;

	@Column(nullable = true)
	private Double handlingFee;

	@Column(nullable = true)
	private Double tax;

	@Column(nullable = true, length = 50)
	private String taxRate;

	@Column(nullable = false)
	private boolean proforma;

	@Column(nullable = true, length = 50)
	private String type;

	@Column(nullable = true, length = 5000)
	private String note;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@Column(nullable = true)
	private Long oldInvoiceId;

	@Column(nullable = true)
	private Integer numberPackages;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_address_id")
	private AddressV2 billingAddress;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private ClientCompanyV2 clientCompany;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_address_id")
	private CompanyShippingAddressV2 shippingAddress;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "client_contact_id")
	private ClientContactV2 clientContact;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "quote_id")
	private QuoteV2 quote;

	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InvoiceLineItemV2> lineItems = new ArrayList<InvoiceLineItemV2>();

	public InvoiceV2() {
		super();
	}

	/**
	 * @return the invoiceId
	 */
	public long getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
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
	 * @return the shippingAttention
	 */
	public String getShippingAttention() {
		return shippingAttention;
	}

	/**
	 * @param shippingAttention the shippingAttention to set
	 */
	public void setShippingAttention(String shippingAttention) {
		this.shippingAttention = shippingAttention;
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
	public double getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * @param currencyRate the currencyRate to set
	 */
	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	 * @return the billingAttention
	 */
	public String getBillingAttention() {
		return billingAttention;
	}

	/**
	 * @param billingAttention the billingAttention to set
	 */
	public void setBillingAttention(String billingAttention) {
		this.billingAttention = billingAttention;
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
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	/**
	 * @param purchaseOrderNumber the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	/**
	 * @return the courier
	 */
	public String getCourier() {
		return courier;
	}

	/**
	 * @param courier the courier to set
	 */
	public void setCourier(String courier) {
		this.courier = courier;
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
	 * @return the shippingTerm
	 */
	public String getShippingTerm() {
		return shippingTerm;
	}

	/**
	 * @param shippingTerm the shippingTerm to set
	 */
	public void setShippingTerm(String shippingTerm) {
		this.shippingTerm = shippingTerm;
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
	 * @return the handleFee
	 */
	public Double getHandlingFee() {
		return handlingFee;
	}

	/**
	 * @param handlingFee the handleFee to set
	 */
	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}

	/**
	 * @return the tax
	 */
	public Double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
	}

	/**
	 * @return the proforma
	 */
	public boolean getProforma() {
		return proforma;
	}

	/**
	 * @param proforma the proforma to set
	 */
	public void setProforma(boolean proforma) {
		this.proforma = proforma;
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
	public List<InvoiceLineItemV2> getLineItems() {
		return lineItems;
	}

	/**
	 * @param lineItems the lineItems to set
	 */
	public void setLineItems(List<InvoiceLineItemV2> lineItems) {
		this.lineItems = lineItems;
	}

	private boolean hasLineItem(InvoiceLineItemV2 lineItemToCheck) {
		boolean found = false;
		if (lineItemToCheck != null) {
			for (InvoiceLineItemV2 lineItem : lineItems) {
				if (lineItem.getInvoiceLineItemId() == lineItemToCheck.getInvoiceLineItemId()) {
					found = true;
				}
			}
		}
		return found;
	}

	private InvoiceLineItemV2 getLineItem(long invoiceLineItemId) {
		InvoiceLineItemV2 aLineItem = null;
		for (InvoiceLineItemV2 lineItem : lineItems) {
			if (lineItem.getInvoiceLineItemId() == invoiceLineItemId) {
				aLineItem = lineItem;
			}
		}
		return aLineItem;
	}

	/**
	 * @param lineItem the lineItem to add to the Invoice
	 */
	public void addLineItem(InvoiceLineItemV2 lineItem) {
		this.lineItems.add(lineItem);
		lineItem.setInvoice(this);
	}

	/**
	 * @return the oldInvoiceId
	 */
	public Long getOldInvoiceId() {
		return oldInvoiceId;
	}

	/**
	 * @param oldInvoiceId the oldInvoiceId to set
	 */
	public void setOldInvoiceId(Long oldInvoiceId) {
		this.oldInvoiceId = oldInvoiceId;
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
//        sb.append("invoiceId=");
//        sb.append(invoiceId);
//        sb.append(",shippingAttention=");
//        sb.append(shippingAttention);
//        sb.append(",active=");
//        sb.append(active);
//        sb.append(",currency=");
//        sb.append(currency);
//        sb.append(",currencyRate=");
//        sb.append(currencyRate);
//        sb.append(",invoiceNumber=");
//        sb.append(invoiceNumber);
//        sb.append(",dateCreated=");
//        sb.append(dateCreated);
//        sb.append(",revision=");
//        sb.append(revision);
//        sb.append(",revisionDate=");
//        sb.append(revisionDate);
//        sb.append(",billingAttention=");
//        sb.append(billingAttention);
//        sb.append(",purchaseOrderNumber=");
//        sb.append(purchaseOrderNumber);
//        sb.append(",courier=");
//        sb.append(courier);
//        sb.append(",paymentType=");
//        sb.append(paymentType);
//        sb.append(",shippingTerm=");
//        sb.append(shippingTerm);
//        sb.append(",shippingFee=");
//        sb.append(shippingFee);
//        sb.append(",handlingFee=");
//        sb.append(handlingFee);
//        sb.append(",tax=");
//        sb.append(tax);
//        sb.append(",proforma=");
//        sb.append(proforma);
//        sb.append(",type=");
//        sb.append(type);
//        sb.append(",note=");
//        sb.append(note);
//        sb.append(",editedBy=");
//        sb.append(editedBy);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        sb.append(",oldInvoiceId=");
//        sb.append(oldInvoiceId);
//        sb.append(",clientCompany=");
//        sb.append(clientCompany);
//        sb.append(",clientContact=");
//        sb.append(clientContact);
//        sb.append(",billingAddress=");
//        sb.append(billingAddress);
//        sb.append(",shippingAddress=");
//        sb.append(",quoteId=");
//        if ( quote != null ) sb.append(quote.getQuoteId());
//        sb.append(shippingAddress);
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
	 * @return the quote
	 */
	public QuoteV2 getQuote() {
		return quote;
	}

	/**
	 * @param quote the quote to set
	 */
	public void setQuote(QuoteV2 quote) {
		this.quote = quote;
	}

	/**
	 * @return the numberPackages
	 */
	public Integer getNumberPackages() {
		return numberPackages;
	}

	/**
	 * @param numberPackages the numberPackages to set
	 */
	public void setNumberPackages(Integer numberPackages) {
		this.numberPackages = numberPackages;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(InvoiceV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newLineItemsStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("shippingAttention", this.getShippingAttention(), obj.getShippingAttention())
				.append("active", this.isActive(), obj.isActive())
				.append("currency", this.getCurrency(), obj.getCurrency())
				.append("currencyRate", this.getCurrencyRate(), obj.getCurrencyRate())
				.append("invoiceNumber", this.getInvoiceNumber(), obj.getInvoiceNumber())
				.append("dateCreated", this.getDateCreated(), obj.getDateCreated())
				.append("revision", this.getRevision(), obj.getRevision())
				.append("revisionDate", this.getRevisionDate(), obj.getRevisionDate())
				.append("billingAttention", this.getBillingAttention(), obj.getBillingAttention())
				.append("purchaseOrderNumber", this.getPurchaseOrderNumber(), obj.getPurchaseOrderNumber())
				.append("courier", this.getCourier(), obj.getCourier())
				.append("paymentType", this.getPaymentType(), obj.getPaymentType())
				.append("shippingTerm", this.getShippingTerm(), obj.getShippingTerm())
				.append("shippingFee", this.getShippingFee(), obj.getShippingFee())
				.append("handlingFee", this.getHandlingFee(), obj.getHandlingFee())
				.append("tax", this.getTax(), obj.getTax()).append("taxRate", this.getTaxRate(), obj.getTaxRate())
				.append("proforma", this.getProforma(), obj.getProforma()).append("type", this.getType(), obj.getType())
				.append("note", this.getNote(), obj.getNote()).append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("oldInvoiceId", this.getOldInvoiceId(), obj.getOldInvoiceId())
				.append("numberPackages", this.getNumberPackages(), obj.getNumberPackages());

		DiffResult billAddressDiffs = this.billingAddress.diff(obj.getBillingAddress());
		db.append("billingAddress", billAddressDiffs);

		DiffResult clientCompanyDiffs = this.clientCompany.diff(obj.getClientCompany());
		db.append("clientCompany", clientCompanyDiffs);

		DiffResult shippingAddressDiffs = this.shippingAddress.diff(obj.getShippingAddress());
		db.append("shippingAddress", shippingAddressDiffs);

		DiffResult clientContactDiffs = this.clientContact.diff(obj.getClientContact());
		db.append("clientContact", clientContactDiffs);

		DiffResult quoteDiffs = this.quote.diff(obj.getQuote());
		db.append("quote", quoteDiffs);

		// iterate over this object contacts and check contact collection from both
		// objects to see what has been added or updated
		// note that no delete check is necessary as we do not delete contacts, simply
		// set them to inactive
		int lineItemIncrementer = 1;
		for (InvoiceLineItemV2 lineItem : lineItems) {
			if (obj.hasLineItem(lineItem)) {
				// check update diffs on same object
				InvoiceLineItemV2 otherLineItem = obj.getLineItem(lineItem.getInvoiceLineItemId());
				DiffResult lineItemDiffs = lineItem.diff(otherLineItem);
				db.append(String.format("LineItem #%d (name:%s)", lineItemIncrementer, lineItem.getName()),
						lineItemDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newLineItemsStringBuilder.append(System.lineSeparator());
				newLineItemsStringBuilder.append(
						String.format("lineItem #%d is new - details : %s", lineItemIncrementer, lineItem.toString()));
			}
			lineItemIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());
		if (newLineItemsStringBuilder.length() > 0) {
			diffStringBuilder.append(newLineItemsStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

}
