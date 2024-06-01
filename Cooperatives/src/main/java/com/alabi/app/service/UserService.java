package com.alabi.app.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alabi.app.dto.UserDTO;
import com.alabi.app.roles.Role;

public interface UserService extends UserDetailsService{

	void create(UserDTO userDTO);
	List<UserDTO> read();
	UserDTO findById(Long id);
	void deleteById(Long id);
	void edit(UserDTO userDTO);
	UserDTO findByEmail(String email);
	java.util.Collection<? extends GrantedAuthority> mapRolesToAuthorities(java.util.Collection<Role> roles);
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	boolean verify(String token);
		
}
