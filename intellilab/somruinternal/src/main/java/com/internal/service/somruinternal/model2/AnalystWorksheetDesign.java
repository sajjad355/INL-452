package com.internal.service.somruinternal.model2;

import com.internal.service.somruinternal.model2.UserV2;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ws_analyst_worksheet_design")
public class AnalystWorksheetDesign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dbid;

	@ManyToOne
	@JoinColumn(name = "analyst_id")
	private UserV2 analyst;

	@ManyToOne
	@JoinColumn(name = "worksheet_design_id")
	private WorksheetDesign worksheetDesign;

	@CreationTimestamp
	private Date assignDate;

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public UserV2 getAnalyst() {
		return analyst;
	}

	public void setAnalyst(UserV2 analyst) {
		this.analyst = analyst;
	}

	public WorksheetDesign getWorksheetDesign() {
		return worksheetDesign;
	}

	public void setWorksheetDesign(WorksheetDesign worksheetDesign) {
		this.worksheetDesign = worksheetDesign;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

}
