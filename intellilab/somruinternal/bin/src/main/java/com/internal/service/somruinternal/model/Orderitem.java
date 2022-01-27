package com.internal.service.somruinternal.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderitem")
@EntityListeners(AuditingEntityListener.class)
public class Orderitem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dbid;

	@Column(nullable = true)
	private String cat;

	@Column(nullable = true)
	private String name;

	@Column(nullable = true)
	private String vendor;

	@Column(nullable = true)
	private int amount;

	@Column(nullable = true)
	private String unit;

	@Column(nullable = true)
	private String grantid;

	@Column(nullable = true)
	private String status;

	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date requesttime;

	@Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date ordertime;

	@Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date receivetime;

	@Column(nullable = true)
	private String urgent;

	public Orderitem(
		Long dbid,
		String cat,
		String name,
		String vendor,
		int amount,
		String unit,
		String grantid,
		String status,
		Date requesttime,
		Date ordertime,
		Date receivetime,
		String urgent){
		super();
		this.dbid = dbid;
		this.cat=cat;
		this.name = name;
		this.vendor = vendor;
		this.amount=amount;
		this.unit=unit;
		this.grantid=grantid;
		this.status=status;
		this.requesttime = requesttime;
		this.ordertime = ordertime;
		this.receivetime = receivetime;
		this.urgent = urgent;
	}

	public Orderitem(){
		super();
	}

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getGrantid() {
		return grantid;
	}

	public void setGrantid(String grantid) {
		this.grantid = grantid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}





}
