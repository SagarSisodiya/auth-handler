package com.invento.authhandler.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.exception.AuthorityNotFoundException;
import com.invento.authhandler.exception.InvalidRequestException;
import com.invento.authhandler.mapper.AuthMapper;
import com.invento.authhandler.model.Authority;
import com.invento.authhandler.repo.AuthorityRepo;
import com.invento.authhandler.service.impl.AuthServiceImpl;
import com.invento.authhandler.utils.TestConstants;
import com.invento.authhandler.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
	private AuthorityRepo authRepo;
	
	@Mock
	private AuthMapper authMapper;
	
	@InjectMocks
	private AuthServiceImpl authService;
	
	@InjectMocks
	private TestUtils testUtils;
	
	
	@Test
	public void testGetAuthortites() {
		
		List<Authority> authorities = testUtils.getAuthorites();
		
		when(authRepo.findAll()).thenReturn(authorities);
		when(authMapper.authoritiesToDtos(Mockito.any()))
			.thenReturn(testUtils.getAuthorityDtos());
		
		List<AuthorityDto> dtos = authService.getAuthorities();
		
		Assertions.assertThat(dtos.get(0).getName())
			.isEqualTo(authorities.get(0).getName());
	}
	
	@Test
	public void testAddAuthorities() {
		
		List<AuthorityDto> dtos = testUtils.getAuthorityDtos();
		
		when(authMapper.dtosToAuthorityList(Mockito.anyList()))
			.thenReturn(testUtils.getAuthorites());
		when(authRepo.saveAll(Mockito.anyList())).thenReturn(testUtils.getAuthorites());
		
		authService.addAuthorites(dtos);
		
		verify(authRepo, times(1)).saveAll(Mockito.anyList());
	}
	
	@Test
	public void testAddAuthorities_invalidRequest() {
		
		List<AuthorityDto> dtos = new ArrayList<>();
	
		Exception ex = assertThrows(InvalidRequestException.class,
			() -> authService.addAuthorites(dtos));
		
		Assertions.assertThat(ex.getMessage()).isEqualTo("Authority dtos are null/empty");
	}
	
	@Test
	public void testgetAuthorityList() {
		
		List<Authority> authoritiesReq = testUtils.getAuthorites();
		
		when(authRepo.findAll()).thenReturn(authoritiesReq);
		
		List<Authority> authoritiesRes = authService.getAuthorityList();
		
		Assertions.assertThat(authoritiesRes.get(0).getName())
			.isEqualTo(authoritiesReq.get(0).getName());
	}
	
	@Test
	public void testUpdateAutority() {
		
		Authority authorityReq = testUtils.getAuthority();
		
		when(authRepo.findById(Mockito.anyInt()))
			.thenReturn(Optional.ofNullable(authorityReq));
		when(authRepo.save(Mockito.any())).thenReturn(authorityReq);
		
		authService.updateAuthority(1, TestConstants.ROLE_TEST_ADMIN);
		
		verify(authRepo, times(1)).save(Mockito.any());
	}
	
	@Test
	public void testUpdateAuthority_invalidRequest() {
		
		Exception ex = assertThrows(InvalidRequestException.class, 
				() -> authService.updateAuthority(1, null));
		
		Assertions.assertThat(ex.getMessage())
			.isEqualTo("Value: null for parameter name is invalid.");
	}
	
	@Test
	public void testUpdateAuthority_authorityNotFound() {
		
		Exception ex = assertThrows(AuthorityNotFoundException.class, 
				() -> authService.updateAuthority(0, TestConstants.ROLE_TEST_ADMIN));
		
		Assertions.assertThat(ex.getMessage())
			.isEqualTo("Authority not found with id 0");
	}
	
	@Test
	public void testDeleteAuthority() {
		
		when(authRepo.findById(Mockito.anyInt()))
			.thenReturn(Optional.ofNullable(testUtils.getAuthority()));
		doNothing().when(authRepo).delete(Mockito.any());
		
		authService.deleteAuthority(1);
		
		verify(authRepo, times(1)).delete(Mockito.any());
	}
	
	@Test
	public void testDeleteAuthority_authorityNotFound() {
		
		
		Exception ex = assertThrows(AuthorityNotFoundException.class,
			() -> authService.deleteAuthority(0));
		
		Assertions.assertThat(ex.getMessage())
			.isEqualTo("Authority not found with id 0");
	}
}
