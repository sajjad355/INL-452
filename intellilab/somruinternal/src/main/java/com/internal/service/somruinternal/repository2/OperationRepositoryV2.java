package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.OperationV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepositoryV2 extends JpaRepository<OperationV2, Long> {
  OperationV2 findDistinctByOperationName(String operationName);
}
