package com.jwt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jwt.dto.UserDTO;
import com.jwt.model.UserModel;
import com.jwt.service.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	AuthService authService;

	@GetMapping("/getAllUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> getAllUser() {
		List<UserModel> user = authService.getAllUsers();
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@GetMapping("/getUser/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<?> getUserById(@PathVariable("id") String userId) {

		UserDTO user = authService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) {
		authService.deleteUserById(userId);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);

	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") String userId, @RequestBody UserModel user) {
		authService.updateUser(userId, user);
		return new ResponseEntity<>("User successfully updated!", HttpStatus.OK);
	}

}
