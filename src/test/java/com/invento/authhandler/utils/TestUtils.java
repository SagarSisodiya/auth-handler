package com.invento.authhandler.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.model.Authority;

@Component
public class TestUtils {

	public List<AuthorityDto> getAuthorityDtos() {
		
		AuthorityDto dto1 = AuthorityDto.builder()
				.id(1)
				.groupId("0af49a49-7313-4acc-82fb-3c4bc467cd79")
				.name("TEST_ADMIN")
				.build();
		
		AuthorityDto dto2 = AuthorityDto.builder()
				.id(2)
				.groupId("38b49c4c-b05c-46b8-a295-745c5c2699f3")
				.name("TEST_RW")
				.build();
		
		AuthorityDto dto3 = AuthorityDto.builder()
				.id(3)
				.groupId("74d7d3f3-faba-4595-93b6-bf4c1065a242")
				.name("TEST_RO")
				.build();
		
		List<AuthorityDto> dtos = Arrays.asList(dto1, dto2, dto3);
		return dtos;
	}
	
	public List<Authority> getAuthorites() {
		
		Authority authority1 = Authority.builder()
				.id(1)
				.groupId("0af49a49-7313-4acc-82fb-3c4bc467cd79")
				.name("TEST_ADMIN")
				.build();
		
		Authority authority2 = Authority.builder()
				.id(2)
				.groupId("38b49c4c-b05c-46b8-a295-745c5c2699f3")
				.name("TEST_RW")
				.build();
		
		Authority authority3 = Authority.builder()
				.id(3)
				.groupId("74d7d3f3-faba-4595-93b6-bf4c1065a242")
				.name("TEST_RO")
				.build();
		
		List<Authority> authorityList = Arrays.asList(authority1, authority2, authority3);
		return authorityList;
	}
	
	
	
	public Authority getAuthority() {
		
		return Authority.builder()
				.id(1)
				.groupId("0af49a49-7313-4acc-82fb-3c4bc467cd79")
				.name("TEST_RW")
				.build();
	}
}
