package com.jwt.service;

import java.util.List;
import com.jwt.dto.UserDTO;
import com.jwt.model.UserModel;

public interface AuthService {

	List<UserModel> getAllUsers();

	UserModel createUser(UserModel user);

	UserDTO getUserById(String userId);

	void deleteUserById(String userId);

	UserModel updateUser(String userId, UserModel user);
	
	
	    	
}
