package com.alabi.app.dto;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alabi.app.config.Encoder;
import com.alabi.app.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDTOMapper implements Function<User, UserDTO> {

	@Autowired
	private final Encoder passwordEncoder;
	
	@Override
	public UserDTO apply(User user) {
		
		return new UserDTO(
				user.getId(),
				user.getFirstName(), 
				user.getLastName(),
				user.getOtherName(),
				user.getEmail(),
				user.getPassword(),
				user.getRoles(),
				user.getCooperatives(),
				false,
				user.getEmailVerificationToken()
				);
	}
	
	public User toUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setOtherName(userDTO.getOtherName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRoles(userDTO.getRoles());
		user.setCooperatives(userDTO.getCooperatives());
		user.setEnabled(userDTO.isEnabled());
		user.setEmailVerificationToken(userDTO.getEmailVerificationToken());
		return user;
	}
}
