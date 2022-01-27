package com.internal.service.somruinternal.dto;

public class Timer {
	private Long timeValueSet;

	private String timeUnitSet;

	private Long timerStartDate;

	private Long timerValue;

	private Integer complete;

	private String formattedTime;
	
	

	public Timer() {
		super();
	}

	public Timer(Long timeValueSet, String timeUnitSet, Long timerStartDate, Long timerValue, Integer complete,
			String formattedTime) {
		super();
		this.timeValueSet = timeValueSet;
		this.timeUnitSet = timeUnitSet;
		this.timerStartDate = timerStartDate;
		this.timerValue = timerValue;
		this.complete = complete;
		this.formattedTime = formattedTime;
	}

	public Long getTimeValueSet() {
		return timeValueSet;
	}

	public void setTimeValueSet(Long timeValueSet) {
		this.timeValueSet = timeValueSet;
	}

	public String getTimeUnitSet() {
		return timeUnitSet;
	}

	public void setTimeUnitSet(String timeUnitSet) {
		this.timeUnitSet = timeUnitSet;
	}

	public Long getTimerStartDate() {
		return timerStartDate;
	}

	public void setTimerStartDate(Long timerStartDate) {
		this.timerStartDate = timerStartDate;
	}

	public Long getTimerValue() {
		return timerValue;
	}

	public void setTimerValue(Long timerValue) {
		this.timerValue = timerValue;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public String getFormattedTime() {
		return formattedTime;
	}

	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}

}
