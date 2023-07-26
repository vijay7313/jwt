package com.jwt.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jwt.Exception.ResourceNotFoundException;
import com.jwt.dao.AuthDAO;
import com.jwt.dto.UserInfoToUserDetailsConvert;
import com.jwt.model.UserModel;

@Component
public class UserDetailService implements UserDetailsService {

	@Autowired
	AuthDAO authDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> user = authDAO.findByUserName(username);
		return user.map(UserInfoToUserDetailsConvert::new)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Name", username));
	}

}
