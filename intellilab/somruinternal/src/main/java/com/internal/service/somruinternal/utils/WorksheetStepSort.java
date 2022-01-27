package com.internal.service.somruinternal.utils;

import java.util.Comparator;

import com.internal.service.somruinternal.model2.WorksheetStep;

public class WorksheetStepSort implements Comparator<WorksheetStep>{

	@Override
	public int compare(WorksheetStep step1, WorksheetStep step2) {
		
		if (step1 == null) {
			return -1;
		} else if (step2 == null) {
			return 1;
		}
		
		return (int) (step1.getPosition() - step2.getPosition());
		
	}

}
