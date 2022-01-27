package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.service.somruinternal.model2.DilutionInstanceSubStep;
import com.internal.service.somruinternal.model2.MaterialInstanceSubStep;

public interface DilutionInstanceSubStepRepository extends JpaRepository<DilutionInstanceSubStep, Long> {

	List<DilutionInstanceSubStep> findByMaterialStep(MaterialInstanceSubStep mdSubStep);

}
