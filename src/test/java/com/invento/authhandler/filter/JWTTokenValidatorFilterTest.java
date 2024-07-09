package com.invento.authhandler.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.invento.authhandler.utils.TestConstants;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidatorFilterTest {
	
	private JWTTokenValidatorFilter filter;
	
	@BeforeEach 
	void setUp() {
		
		filter = new JWTTokenValidatorFilter();
	}
	
	@Test
	public void testDoFilter_JwtException() throws ServletException, IOException {
		
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        
        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getServletPath()).thenReturn("/");
        
        Assertions.assertThrows(JwtException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_SwaggerURI() throws ServletException, IOException {
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        
        when(mockReq.getRequestURI()).thenReturn(TestConstants.SWAGGER_UI);
        when(mockReq.getServletPath()).thenReturn("/");

        filter.doFilter(mockReq, mockResp, mockFilterChain);
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_InvalidJwt() throws ServletException, IOException {
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        
        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getServletPath()).thenReturn("/");
        when(mockReq.getHeader(TestConstants.JWT_HEADER))
        	.thenReturn(TestConstants.INVALID_MOCK_JWT_TOKEN);
        
        Assertions.assertThrows(SignatureException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_JwtWithBearer() throws ServletException, IOException {
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        
        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getServletPath()).thenReturn("/");
        when(mockReq.getHeader(TestConstants.JWT_HEADER))
        	.thenReturn(TestConstants.BEARER + TestConstants.INVALID_MOCK_JWT_TOKEN);
        
        Assertions.assertThrows(SignatureException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
}
