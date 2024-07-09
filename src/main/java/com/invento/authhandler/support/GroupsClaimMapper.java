package com.invento.authhandler.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;

import com.invento.authhandler.model.Authority;

public class GroupsClaimMapper {

	private final String authoritiesPrefix;
	private final String groupsClaim;
	private final List<Authority> groupToAuthorities;
	
	public GroupsClaimMapper(String  authoritiesPrefix, String groupsClaim, List<Authority> groupToAuthorities) {
		this.authoritiesPrefix = authoritiesPrefix;
		this.groupsClaim = groupsClaim;
		this.groupToAuthorities = Collections.unmodifiableList(groupToAuthorities);
	}
	
	public Collection<? extends GrantedAuthority> mapAuthorities(ClaimAccessor source) {
		
		List<String> groups = source.getClaimAsStringList(groupsClaim);
		if(groups == null || groups.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<GrantedAuthority> result = new ArrayList<>();
		for(String g: groups) {
			List<SimpleGrantedAuthority> authNames = groupToAuthorities.stream()
					.filter(ga -> ga.getGroupId().equals(g))
					.map(ga -> authoritiesPrefix + ga.getName())
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			result.addAll(authNames);
		}
		
//		result = groups.stream().filter(g -> groupToAuthorities.contains(g)).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		
		return result;
	}
}
