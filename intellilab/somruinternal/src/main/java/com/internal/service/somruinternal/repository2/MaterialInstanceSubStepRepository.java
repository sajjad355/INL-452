package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.MaterialInstanceSubStep;
import com.internal.service.somruinternal.model2.TestStep;

@Repository
public interface MaterialInstanceSubStepRepository extends JpaRepository<MaterialInstanceSubStep, Long> {

	List<MaterialInstanceSubStep> findByCaptureStep(CaptureStep wsStep);

	List<MaterialInstanceSubStep> findByCoatingStep(CoatingStep wsStep);

	List<MaterialInstanceSubStep> findByDetectionStep(DetectionStep wsStep);

	List<MaterialInstanceSubStep> findByTestStep(TestStep wsStep);

}
