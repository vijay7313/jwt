package com.jwt.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.Exception.ResourceNotFoundException;
import com.jwt.dao.AuthDAO;
import com.jwt.dto.UserDTO;
import com.jwt.model.UserModel;
import com.jwt.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	AuthDAO authDAO;
	

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired(required = true)
	ModelMapper modelMapper;

	@Override
	public List<UserModel> getAllUsers() {
		return (List<UserModel>) authDAO.findAll();
	}

	@Override
	public UserModel createUser(UserModel user) {
		UUID uuid = UUID.randomUUID();
		user.setUserId(uuid.toString());
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return authDAO.save(user);
	}

	@Override
	public UserDTO getUserById(String userId) {
		UserModel user = authDAO.findById(userId)
				.orElseThrow(() -> new com.jwt.Exception.ResourceNotFoundException("User", "id", userId));

		UserDTO registerDTO = modelMapper.map(user, UserDTO.class);
		return registerDTO;
	}

	@Override
	public void deleteUserById(String userId) {
		UserModel user = authDAO.findById(userId)
				.orElseThrow(() -> new com.jwt.Exception.ResourceNotFoundException("User", "id", userId));

		authDAO.delete(user);

	}

	@Override
	public UserModel updateUser(String userId, UserModel user) {
		UserModel userDetail = authDAO.findById(userId)
				.orElseThrow(() -> new com.jwt.Exception.ResourceNotFoundException("User", "id", userId));

		return authDAO.save(userDetail);
	}
}
