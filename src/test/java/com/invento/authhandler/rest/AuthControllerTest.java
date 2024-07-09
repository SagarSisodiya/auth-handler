package com.invento.authhandler.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.service.AuthService;
import com.invento.authhandler.utils.TestConstants;
import com.invento.authhandler.utils.TestUtils;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AuthService authService;
	
	@InjectMocks
	private TestUtils testUtils;
	
	@Test
	public void testGetAuthorities() throws Exception {
		
		List<AuthorityDto> dtos  = testUtils.getAuthorityDtos();
		
		when(authService.getAuthorities()).thenReturn(dtos);
		
		ResultActions response = mockMvc.perform(get("/auth/getAuthorities")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.[0].name", CoreMatchers.is(dtos.get(0).getName())));
	}
	
	@Test
	public void testLogin() throws JsonProcessingException, Exception {
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				TestConstants.TEST_USER, TestConstants.TEST_PASS);
		
		ResultActions response = mockMvc.perform(get("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authentication)));
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Authentication Successful")));
	}
	
	@Test
	public void testGetAuthorities_notFound() throws Exception {
		
		when(authService.getAuthorities()).thenReturn(new ArrayList<>());
		
		ResultActions response = mockMvc.perform(get("/auth/getAuthorities")
			.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testAddAuthority() throws Exception {
		
		List<AuthorityDto> dtos  = testUtils.getAuthorityDtos();
		
		doNothing().when(authService).addAuthorites(Mockito.anyList());
		
		ResultActions response = mockMvc.perform(post("/auth/addAuthority")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(dtos))); 
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Authorities created successfully")));
	}
	
	@Test
	public void testUpdateAuthority() throws Exception {
		
		doNothing().when(authService).updateAuthority(Mockito.anyInt(), Mockito.anyString());
		
		ResultActions response = mockMvc.perform(put("/auth/updateAuthority")
				.contentType(MediaType.APPLICATION_JSON)
				.param("id", "1")
				.param("name", "Test_RO")); 
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Authority updated successfully")));
	}
	
	@Test
	public void testDeleteAuthority() throws Exception {
		
		doNothing().when(authService).deleteAuthority(Mockito.anyInt());
		
		ResultActions response = mockMvc.perform(delete("/auth/deleteAuthority")
				.contentType(MediaType.APPLICATION_JSON)
				.param("id", "1")); 
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(
				"$.message", CoreMatchers.is("Authority deleted successfully")));
	}
}
