package com.internal.service.somruinternal.utils;

import java.util.Comparator;

import com.internal.service.somruinternal.model2.DilutionInstanceSubStep;

public class DilutionInstanceSubStepSort implements Comparator<DilutionInstanceSubStep> {

	@Override
	public int compare(DilutionInstanceSubStep subStep1, DilutionInstanceSubStep subStep2) {
		if (subStep1 == null) {
			return -1;
		} else if (subStep2 == null) {
			return 1;
		}

		return ((subStep1.getPosition() != null ? subStep1.getPosition() : 0)
				- (subStep2.getPosition() != null ? subStep2.getPosition() : 0));
	}

}
