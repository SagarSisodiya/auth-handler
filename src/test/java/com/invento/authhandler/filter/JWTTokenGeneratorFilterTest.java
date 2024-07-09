package com.invento.authhandler.filter;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.invento.authhandler.utils.TestConstants;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilterTest {

	@Test
	public void testDoFilter() throws ServletException, IOException, java.io.IOException {
		
		JWTTokenGeneratorFilter filter = new JWTTokenGeneratorFilter();
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        
        when(mockReq.getServletPath()).thenReturn("/auth/login");
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
				TestConstants.TEST_USER, TestConstants.TEST_PASS);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
      	filter.doFilter(mockReq, mockResp, mockFilterChain);
        filter.destroy();
        SecurityContextHolder.getContext().setAuthentication(null);
	}
}
