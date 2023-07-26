package com.jwt.configure.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.service.impl.JWTServiceImpl;
import com.jwt.service.impl.ResponseHandler;
import com.jwt.service.impl.UserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	JWTServiceImpl jwtServiceImpl;

	@Autowired
	UserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 Map<String, Object> responseData = new HashMap<>();
		 
		try {

			String authHeader = request.getHeader("Authorization");
			String token = null,userName = null;
			
			if (authHeader != null && authHeader.startsWith("Bearer ")) 
			{
				
				token = authHeader.substring(7);
				userName = jwtServiceImpl.extractUsername(token);
				
			}
			
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) 
			{
				
				UserDetails userDetails = userDetailService.loadUserByUsername(userName);
				
				boolean tokenValidation=jwtServiceImpl.validateToken(token, userDetails);
				
				if (tokenValidation) 
				{
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		catch (ExpiredJwtException expiredJwtException) {
		   
		    responseData.put("message", "Token expired");
		    responseData.put("statusCode", 401);

		    Map<String, Object> responseMap = new HashMap<>();
		    
		    responseMap.put("responseData", responseData);
		    responseMap.put("apiStatus", "");

//		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.setContentType("application/json");
		    response.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));
		    
		    return;
		} 
		catch (AuthenticationException authenticationException) {
		    responseData.put("message", "Authentication failed");
		    responseData.put("statusCode", 401);

		    Map<String, Object> responseMap = new HashMap<>();
		    responseMap.put("responseData", responseData);
		    responseMap.put("apiStatus", "");

//		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.setContentType("application/json");
		    response.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));

		    return;
		}
	
		filterChain.doFilter(request, response);
	}
}