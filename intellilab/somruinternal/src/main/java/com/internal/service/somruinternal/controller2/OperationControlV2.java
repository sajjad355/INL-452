package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.OperationV2;
import com.internal.service.somruinternal.repository2.OperationRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

@RestController
@RequestMapping("/operationV2")
public class OperationControlV2 {
  private final static Logger LOGGER = LoggerFactory.getLogger(OperationControlV2.class);

  final OperationRepositoryV2 operationRepository;

  @Autowired
  public OperationControlV2(
    OperationRepositoryV2 operationRepository
  ) {
    this.operationRepository = operationRepository;
  }

  @GetMapping("/all")
  public List<OperationV2> getAllOperations() {
    LOGGER.info("/operationV2 received GET /all mapped to getAllOperations");
    return operationRepository.findAll();
  }
}
