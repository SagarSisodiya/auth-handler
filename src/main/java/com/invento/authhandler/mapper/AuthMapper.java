package com.invento.authhandler.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.model.Authority;

@Component
public class AuthMapper {

	public List<Authority> dtosToAuthorityList(List<AuthorityDto> dtos) {
		
		List<Authority> authoties = new ArrayList<>();
		
		authoties = dtos.stream().map(a -> {				
			return Authority.builder()
					.groupId(a.getGroupId())
					.name(a.getName())
					.build();
		}).collect(Collectors.toList());
		
		return authoties;
	}
	
	
	public List<AuthorityDto> authoritiesToDtos(List<Authority> authorities) {
		
		List<AuthorityDto> authorityDtos = new ArrayList<>(); 
		
		authorityDtos = authorities.stream().map(a -> {
			return AuthorityDto.builder()
					.id(a.getId())
					.groupId(a.getGroupId())
					.name(a.getName())
					.build();
		}).collect(Collectors.toList());
		
		return authorityDtos;
	}
}
