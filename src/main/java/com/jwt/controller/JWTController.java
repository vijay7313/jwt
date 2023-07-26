package com.jwt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.jwt.dto.AuthJWTRequest;
import com.jwt.model.UserModel;
import com.jwt.service.AuthService;
import com.jwt.service.JWTService;
import com.jwt.service.impl.ResponseHandler;

@RestController
@CrossOrigin
public class JWTController {

	@Autowired
	JWTService jwtService;

	@Autowired
	AuthService authService;

	@Autowired
	ResponseHandler responseHandler;

	@Autowired(required = true)
	AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthJWTRequest authJWTRequest) {
		
		Map<String, Object> apiData = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<>();
		
		try {
		
			Authentication authenticate = 
					authenticationManager.
					authenticate(new UsernamePasswordAuthenticationToken(
					authJWTRequest.getUserName(), authJWTRequest.getPassword()));
			
			String token = jwtService.generateToken(authJWTRequest.getUserName());
			
			apiData.put("message", "LoggedIn Successfully");
			apiData.put("statusCode", HttpStatus.OK.value());
			
			return ResponseHandler.generateResponse(token, apiData, null);
		} 
		catch (BadCredentialsException badCredentialsException) {
			errorData.put("message", "Incorrect password");
			errorData.put("statusCode", HttpStatus.UNAUTHORIZED.value());
			
			return ResponseHandler.generateResponse("", "", errorData);
		} 
		catch (AuthenticationException authenticationException) {
			errorData.put("message", "Invalid username");
			errorData.put("statusCode", HttpStatus.UNAUTHORIZED.value());
			
			return ResponseHandler.generateResponse("", "", errorData);
		}

	}
	
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody UserModel registerUser) {
		System.out.println("1");
		authService.createUser(registerUser);
		return new ResponseEntity<>("User Created successfully!", HttpStatus.CREATED);
	}

}
