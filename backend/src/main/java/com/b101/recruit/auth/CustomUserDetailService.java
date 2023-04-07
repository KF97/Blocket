package com.b101.recruit.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.b101.recruit.domain.entity.User;
import com.b101.recruit.service.impl.UserService;


@Component
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userService.findByUserEmail(userEmail);
		if(user!=null) {
			CustomUserDetails customUserDetails = new CustomUserDetails(user);
			return customUserDetails;
		}
		return null;
	}
	
	
}
