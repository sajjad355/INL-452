package com.internal.service.somruinternal.model2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_design_plate_map")
public class PlateMap {
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "fk_worksheet_id")
	@JsonIgnore
	private WorksheetDesign worksheet;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dbid;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private String data;
	
	@Column
	private String name;
	
	@Column
	private String type;

	public PlateMap() {
		super();
	}

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public String getData() {
		return data;
	}

	public void setData(String file) {
		this.data = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public WorksheetDesign getWorksheet() {
		return worksheet;
	}

	public void setWorksheet(WorksheetDesign worksheet) {
		this.worksheet = worksheet;
	}
}
