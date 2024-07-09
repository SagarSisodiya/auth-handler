package com.invento.authhandler.service;

import java.util.List;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.model.Authority;

public interface AuthService {
		
	public List<AuthorityDto> getAuthorities();
	
	public void addAuthorites(List<AuthorityDto> authorityDtos);
	
	public List<Authority> getAuthorityList();
	
	public void updateAuthority(int id, String name);
	
	public void deleteAuthority(int id);
}
