package com.internal.service.somruinternal.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.internal.service.somruinternal.model2.UserRoleV2;
import com.internal.service.somruinternal.model2.UserV2;

public class SomruUserDetails implements UserDetails{
	
	private List<GrantedAuthority> authorities;
	
	String username;
	
	String password;
	
	boolean accountNonExpired;
	
	boolean accountNonLocked;
	
	boolean credentialsNonExpired;
	
	boolean enabled;
	
	public SomruUserDetails(UserV2 user) {
		Set<UserRoleV2>  userRoles = user.getUserRoles();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserRoleV2 userRole : userRoles) {
			GrantedAuthority grant = new SimpleGrantedAuthority(userRole.getRoleName());
			authorities.add(grant);
		}
		setAuthorities(authorities);
		setAccountNonExpired(true);
		setAccountNonLocked(true);
		setCredentialsNonExpired(true);
		setEnabled(user.isActive());
		setPassword(user.getPassword());
		setUsername(user.getEmail());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
