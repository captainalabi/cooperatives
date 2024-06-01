package com.alabi.app.dto;

import java.util.Collection;
import java.util.List;

import com.alabi.app.entity.Cooperatives;
import com.alabi.app.roles.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{

	private Long id;
	private String firstName;
	private String lastName;
	private String otherName;
	private String email;	
	private String password;
	private Collection <Role> roles;	
	private List<Cooperatives> cooperatives;
	@Builder.Default 
	private boolean enabled = false;
	private String emailVerificationToken;
}
