package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.UserRoleV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepositoryV2 extends JpaRepository<UserRoleV2, Long> {
  UserRoleV2 findDistinctByRoleName(String roleName);

UserRoleV2 findByUserRoleId(Long userRoleID);
}
