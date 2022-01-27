package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.OperationV2;
import com.internal.service.somruinternal.model2.UserRoleV2;
import com.internal.service.somruinternal.repository2.OperationRepositoryV2;
import com.internal.service.somruinternal.repository2.UserRoleRepositoryV2;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

@RestController
@RequestMapping("/userRoleV2")
public class UserRoleControlV2 extends ParentControl {
  private final static Logger LOGGER = LoggerFactory.getLogger(UserRoleControlV2.class);

  final UserRoleRepositoryV2 userRoleRepository;
  final OperationRepositoryV2 operationRepository;

  @Autowired
  public UserRoleControlV2(
    UserRoleRepositoryV2 userRoleRepository,
    OperationRepositoryV2 operationRepository) {
    this.userRoleRepository = userRoleRepository;
    this.operationRepository = operationRepository;
  }

  @GetMapping("/all")
  public List<UserRoleV2> getAllUserRoles() {
    LOGGER.info("/userRoleV2 received GET /all mapped to getAllUserRoles");
    List<UserRoleV2> allUserRoles = userRoleRepository.findAll();
    for (UserRoleV2 userRole : allUserRoles) {
      Hibernate.initialize(userRole.getOperations());
    }
    return allUserRoles;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserRoleV2> update(@PathVariable(value = "id") Long userRoleID,
                                           @Valid @RequestBody UserRoleV2 newUserRole) {
    LOGGER.info(
      String.format(
        "/userRoleV2 received PUT /update/{id} on newUserRole %s",
        newUserRole.toString()
      )
    );
    UserRoleV2 userRoleInDatabase = userRoleRepository.findByUserRoleId(userRoleID);
    if ( userRoleInDatabase == null ) { return ResponseEntity.notFound().build(); }
    Set<OperationV2> newOperations = new HashSet<>();
    for ( OperationV2 newOperation : newUserRole.getOperations() ) {
      OperationV2 operationInDatabase = operationRepository.findDistinctByOperationName(newOperation.getOperationName());
      if ( operationInDatabase == null ) { return ResponseEntity.notFound().build(); }
      newOperations.add(operationInDatabase);
    }
    userRoleInDatabase.setOperations(newOperations);
    UserRoleV2 updatedUserRole = userRoleRepository.save(userRoleInDatabase);
    super.saveUserHistory(
      "",
      "User",
      "userRoleRepository.save(userRoleInDatabase)",
      String.format("Updated the role %s", updatedUserRole.getRoleName())
    );
    return ResponseEntity.ok(updatedUserRole);
  }
}
