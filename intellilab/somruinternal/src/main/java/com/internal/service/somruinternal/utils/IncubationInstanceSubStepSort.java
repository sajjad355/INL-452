package com.internal.service.somruinternal.utils;

import java.util.Comparator;

import com.internal.service.somruinternal.model2.IncubationInstanceSubStep;

public class IncubationInstanceSubStepSort implements Comparator<IncubationInstanceSubStep> {

	@Override
	public int compare(IncubationInstanceSubStep subStep1, IncubationInstanceSubStep subStep2) {
		if (subStep1 == null) {
			return -1;
		} else if (subStep2 == null) {
			return 1;
		}

		return ((subStep1.getPosition() != null ? subStep1.getPosition() : 0)
				- (subStep2.getPosition() != null ? subStep2.getPosition() : 0));
	}

}
