package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.Diffable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ToStringExclude;

@Entity
@Table(name = "il_user")
public class UserV2 implements Diffable<UserV2> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(nullable = false, length = 100)
	private String name;

	@ToStringExclude
	@Column(nullable = true, length = 100)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false)
	private long invalidlogincount;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false, length = 50)
	private String editedBy;

	@Column(nullable = false)
	private Date modifiedOn;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "il_user_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_role_id") })
	private Set<UserRoleV2> userRoles = new HashSet<UserRoleV2>();

	public UserV2() {
		super();
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the invalidlogincount
	 */
	public long getInvalidlogincount() {
		return invalidlogincount;
	}

	/**
	 * @param invalidlogincount the invalidlogincount to set
	 */
	public void setInvalidlogincount(long invalidlogincount) {
		this.invalidlogincount = invalidlogincount;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the editedBy
	 */
	public String getEditedBy() {
		return editedBy;
	}

	/**
	 * @param editedBy the editedBy to set
	 */
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the userRoles
	 */
	public Set<UserRoleV2> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(Set<UserRoleV2> userRoles) {
		this.userRoles = userRoles;
	}

	private boolean hasUserRole(UserRoleV2 userRoleToCheck) {
		boolean found = false;
		if (userRoleToCheck != null) {
			for (UserRoleV2 userRole : userRoles) {
				if (userRole.getUserRoleId() == userRoleToCheck.getUserRoleId()) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * @param addressId the addressId of the address to return
	 * @return CompanyShippingAddressV2 if the collection of shipping addresses has
	 *         a record with this id. If not found, return null
	 */
	private UserRoleV2 getUserRole(long userRoleID) {
		UserRoleV2 aUserRole = null;
		for (UserRoleV2 sa : userRoles) {
			if (sa.getUserRoleId() == userRoleID) {
				aUserRole = sa;
			}
		}
		return aUserRole;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String diffCompare(UserV2 obj) {
		StringBuilder diffStringBuilder = new StringBuilder();
		StringBuilder newUserRoleStringBuilder = new StringBuilder();

		DiffBuilder db = new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName()).append("password", this.getPassword(), obj.getPassword())
				.append("email", this.getEmail(), obj.getEmail())
				.append("invalidlogincount", this.getInvalidlogincount(), obj.getInvalidlogincount())
				.append("active", this.isActive(), obj.isActive())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn());

		int userRoleIncrementer = 1;
		for (UserRoleV2 userRole : userRoles) {
			if (obj.hasUserRole(userRole)) {
				// check update diffs on same object
				UserRoleV2 otherUserRole = obj.getUserRole(userRole.getUserRoleId());
				DiffResult userRoleDiffs = userRole.diff(otherUserRole);
				db.append(String.format("UserRole #%d (name:%s)", userRoleIncrementer, userRole.getRoleName()),
						userRoleDiffs);
			} else {
				// collect delta of new Contacts and append directly to diffSB
				newUserRoleStringBuilder.append(System.lineSeparator());
				newUserRoleStringBuilder.append(
						String.format("UserRole #%d is new - details : %s", userRoleIncrementer, userRole.toString()));
			}
			userRoleIncrementer++;
		}

		// join all the differences together
		diffStringBuilder.append(db.build().toString());

		if (newUserRoleStringBuilder.length() > 0) {
			diffStringBuilder.append(newUserRoleStringBuilder.toString());
		}
		return diffStringBuilder.toString();
	}

	@Override
	public DiffResult diff(UserV2 obj) {
		// No need for null check, as NullPointerException correct if obj is null
		return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
				.append("name", this.getName(), obj.getName()).append("password", this.getPassword(), obj.getPassword())
				.append("email", this.getEmail(), obj.getEmail())
				.append("invalidlogincount", this.getInvalidlogincount(), obj.getInvalidlogincount())
				.append("active", this.isActive(), obj.isActive())
				.append("userRoles ", this.getUserRoles(), obj.getUserRoles())
				.append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn())
				.append("editedBy", this.getEditedBy(), obj.getEditedBy()).build();

	}

}
