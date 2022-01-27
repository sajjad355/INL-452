package com.internal.service.somruinternal.model2;

import javax.persistence.*;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "il_user_role")
public class UserRoleV2 implements Diffable<UserRoleV2> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userRoleId;

  @Column(nullable = false, unique = true,length=50)
  private String roleName;


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "il_role_operation_mapping",
    joinColumns = { @JoinColumn(name = "user_role_id") },
    inverseJoinColumns = { @JoinColumn(name = "operation_id") }
  )
  private Set<OperationV2> operations = new HashSet<>();

  public UserRoleV2() {
      super();
  }

  /**
   * @return the userRoleId
   */
  public long getUserRoleId() {
      return userRoleId;
  }

  /**
   * @param userRoleId the userRoleId to set
   */
  public void setUserRoleId(long userRoleId) {
      this.userRoleId = userRoleId;
  }

  /**
   * @return the roleName
   */
  public String getRoleName() {
      return roleName;
  }

  /**
   * @param roleName the roleName to set
   */
  public void setRoleName(String roleName) {
      this.roleName = roleName;
  }

  /**
   * @return the user's allowed operations
   */
  public Set<OperationV2> getOperations() { return operations; }

  /**
   * @param operations the allowed operations to set
   */
  public void setOperations(Set<OperationV2> operations) { this.operations = operations; }

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public DiffResult diff(UserRoleV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("roleName", this.getRoleName(), obj.getRoleName())
				.append("operations", this.getOperations(), obj.getOperations()).build();

	}


}
