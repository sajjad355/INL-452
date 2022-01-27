package com.internal.service.somruinternal.dto;

public class WorksheetDesignPostOutput {
	String message;
	Long dbid;
	Long version;

	public WorksheetDesignPostOutput(String message, Long dbid, Long version) {
		super();
		this.message = message;
		this.dbid = dbid;
		this.version = version;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
