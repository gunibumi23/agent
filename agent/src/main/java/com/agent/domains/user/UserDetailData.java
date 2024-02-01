package com.agent.domains.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserDetailData implements UserDetails {
	
	private String userCd;
	private String userPw;
	private String userNm;
	private String userSts;
	private String teamCd;
	private String userPh;
	private String useYn;
	private String delYn;
	private String createdDt;
	private String createdCd;
	private String createdNm;
	private String updatedDt;
	private String updatedCd;
	private String updatedNm;
	

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return this.userPw;
	}

	@Override
	public String getUsername() {
		return this.userCd;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return "NORMAL".equalsIgnoreCase(getUserSts());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {		
		return "N".equalsIgnoreCase(getDelYn());
	}
}
