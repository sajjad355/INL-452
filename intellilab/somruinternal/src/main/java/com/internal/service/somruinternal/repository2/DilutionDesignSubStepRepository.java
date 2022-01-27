package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internal.service.somruinternal.model2.DilutionDesignSubStep;
import com.internal.service.somruinternal.model2.MaterialDesignSubStep;

public interface DilutionDesignSubStepRepository extends JpaRepository<DilutionDesignSubStep, Long> {

	List<DilutionDesignSubStep> findByMaterialStep(MaterialDesignSubStep mdSubStep);

}
