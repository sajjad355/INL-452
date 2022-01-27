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
@Table(name = "il_quote_line_item")
public class QuoteLineItemV2 implements Diffable<QuoteLineItemV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quoteLineItemId;

	@Column(nullable = false, length = 50)
	private String catalogNumber;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false)
	private double price;

	@Column(nullable = true, length = 50)
	private String size;

	@Column(nullable = true)
	private double itemDiscount;

	@Column(nullable = true)
	private double itemQuantity;

	@Column(nullable = false)
	private double totalPrice;

	@Column(nullable = true, length = 5000)
	private String footNote;

	@Column(nullable = false, length = 50)
	private String editby;

	@Column(nullable = false)
	private Date modifiedOn;

	@ManyToOne(optional = false)
	@JoinColumn(name = "quote_id")
	@JsonBackReference
	private QuoteV2 quote;

	@ManyToOne(optional = false)
	@JoinColumn(name = "sales_item_id")
	private SalesItemV2 salesItem;

	public QuoteLineItemV2() {
		super();
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
	 * @return the quoteLineItemId
	 */
	public long getQuoteLineItemId() {
		return quoteLineItemId;
	}

	/**
	 * @param quoteLineItemId the quoteLineItemId to set
	 */
	public void setQuoteLineItemId(long quoteLineItemId) {
		this.quoteLineItemId = quoteLineItemId;
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the itemDiscount
	 */
	public double getItemDiscount() {
		return itemDiscount;
	}

	/**
	 * @param itemDiscount the itemDiscount to set
	 */
	public void setItemDiscount(double itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	/**
	 * @return the itemQuantity
	 */
	public double getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * @param itemQuantity the itemQuantity to set
	 */
	public void setItemQuantity(double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the footNote
	 */
	public String getFootNote() {
		return footNote;
	}

	/**
	 * @param footNote the footNote to set
	 */
	public void setFootNote(String footNote) {
		this.footNote = footNote;
	}

	/**
	 * @return the editby
	 */
	public String getEditby() {
		return editby;
	}

	/**
	 * @param editby the editby to set
	 */
	public void setEditby(String editby) {
		this.editby = editby;
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
	 * @return the salesItem
	 */
	public SalesItemV2 getSalesItem() {
		return salesItem;
	}

	/**
	 * @param salesItem the salesItem to set
	 */
	public void setSalesItem(SalesItemV2 salesItem) {
		this.salesItem = salesItem;
	}

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("quoteLineItemId=");
//        sb.append(quoteLineItemId);
//        sb.append(",catalogNumber=");
//        sb.append(catalogNumber);
//        sb.append(",name=");
//        sb.append(name);
//        sb.append(",price=");
//        sb.append(price);
//        sb.append(",size=");
//        sb.append(size);
//        sb.append(",itemDiscount=");
//        sb.append(itemDiscount);
//        sb.append(",itemQuantity=");
//        sb.append(itemQuantity);
//        sb.append(",totalPrice=");
//        sb.append(totalPrice);
//        sb.append(",footNote=");
//        sb.append(footNote);
//        sb.append(",editby=");
//        sb.append(editby);
//        sb.append(",modifiedOn=");
//        sb.append(modifiedOn);
//        sb.append(",salesItem=");
//        sb.append(salesItem);
//        sb.append("\n");
//        return sb.toString();
//    }

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(QuoteLineItemV2 obj) {
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("catalogNumber", this.getCatalogNumber(), obj.getCatalogNumber())
				.append("name", this.getName(), obj.getName()).append("price", this.getPrice(), obj.getPrice())
				.append("size", this.getSize(), obj.getSize())
				.append("itemDiscount", this.getItemDiscount(), obj.getItemDiscount())
				.append("itemQuantity", this.getItemQuantity(), obj.getItemQuantity())
				.append("totalPrice", this.getTotalPrice(), obj.getTotalPrice())
				.append("footNote", this.getFootNote(), obj.getFootNote())
				.append("salesItem", this.getSalesItem(), obj.getSalesItem())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("editedBy", this.getEditby(), obj.getEditby()).build();
	}

}
