package com.internal.service.somruinternal.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Eggtransfer")
public class Eggtransfer {

	public Eggtransfer() {
		super();
	}

	public Eggtransfer(Long dbid, String chickenid, String eggdbid, String action, String egguseamount, String destinationtable, String destinationdbid, String editby, Date modify) {
		super();
		this.dbid = dbid;
		this.chickenid = chickenid;
		this.eggdbid = eggdbid;
		this.action = action;
		this.egguseamount = egguseamount;
		this.destinationtable = destinationtable;
		this.destinationdbid = destinationdbid;
		this.editby = editby;
		this.modify = modify;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dbid;

	@Column(nullable = true)
	private String chickenid;

	@Column(nullable = true)
	private String eggdbid;

	@Column(nullable = true)
	private String action;

	@Column(nullable = true)
	private String egguseamount;

	@Column(nullable = true)
	private String destinationtable;

	@Column(nullable = true)
	private String destinationdbid;

	@Column(nullable = true)
	private String editby;

	@Column(nullable = true)
	private Date modify;

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public String getChickenid() {
		return chickenid;
	}

	public void setChickenid(String chickenid) {
		this.chickenid = chickenid;
	}

	public String getEggdbid() {
		return eggdbid;
	}

	public void setEggdbid(String eggdbid) {
		this.eggdbid = eggdbid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEgguseamount() {
		return egguseamount;
	}

	public void setEgguseamount(String egguseamount) {
		this.egguseamount = egguseamount;
	}

	public String getDestinationtable() {
		return destinationtable;
	}

	public void setDestinationtable(String destinationtable) {
		this.destinationtable = destinationtable;
	}

	public String getDestinationdbid() {
		return destinationdbid;
	}

	public void setDestinationdbid(String destinationdbid) {
		this.destinationdbid = destinationdbid;
	}

	public String getEditby() {
		return editby;
	}

	public void setEditby(String editby) {
		this.editby = editby;
	}

	public Date getModify() {
		return modify;
	}

	public void setModify(Date modify) {
		this.modify = modify;
	}

	@Override
	public String toString() {
		return "Chicken [dbid=" + dbid + ", chickenid=" + chickenid + ", eggdbid=" + eggdbid + ", action=" + action + ", egguseamount=" + egguseamount + ",  destinationtable=" + destinationtable + ", destinationdbid=" + destinationdbid + ", editby=" + editby + "]";
	}

}
