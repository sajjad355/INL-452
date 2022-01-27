package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.MaterialDesignSubStep;
import com.internal.service.somruinternal.model2.TestStep;

@Repository
public interface MaterialDesignSubStepRepository extends JpaRepository<MaterialDesignSubStep, Long> {

	List<MaterialDesignSubStep> findByCaptureStep(CaptureStep wsStep);

	List<MaterialDesignSubStep> findByCoatingStep(CoatingStep wsStep);

	List<MaterialDesignSubStep> findByDetectionStep(DetectionStep wsStep);

	List<MaterialDesignSubStep> findByTestStep(TestStep wsStep);

}
