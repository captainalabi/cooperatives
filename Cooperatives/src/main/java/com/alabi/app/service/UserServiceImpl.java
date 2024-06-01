package com.alabi.app.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alabi.app.dto.UserDTO;
import com.alabi.app.dto.UserDTOMapper;
import com.alabi.app.entity.User;
import com.alabi.app.repository.UserRepository;
import com.alabi.app.roles.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final UserDTOMapper userDTOMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO dto = findByEmail(username);
		if (dto != null) {
			return new org.springframework.security.core.userdetails.User(
					dto.getEmail(),
					dto.getPassword(),
					mapRolesToAuthorities(dto.getRoles()));
		} else {
			throw new UsernameNotFoundException("Username Not Found!");
		}
	}
	
	 public Collection<? extends GrantedAuthority>
	 mapRolesToAuthorities(Collection<Role> roles) {
		 return roles.stream()
	 .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors
	 .toList()); }
	

	@Override
	public List<UserDTO> read() {
		return userRepository.findAll().stream().map(userDTOMapper).collect(Collectors.toList());
	}

	@Override
	public UserDTO findById(Long id) {
		return userRepository.findById(id).map(userDTOMapper).get();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public void create(UserDTO userDTO) {
		User user = userDTOMapper.toUser(userDTO);
		userRepository.save(user);
	}
/*
	@Override
	public void edit(UserDTO userDTO) {
		User theUser = userRepository.findById(userDTO.getId()).get();
		User user = userDTOMapper.toUser(userDTO);
		user.setPassword(theUser.getPassword());
		userRepository.save(theUser);
	}
	*/
	@Override
	public void edit(UserDTO userDTO) {
		User theUser = userRepository.findById(userDTO.getId()).get();
		theUser.setRoles(userDTO.getRoles());
		userRepository.save(theUser);
	}

	@Override
	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return userDTOMapper.apply(user);
	}

	/*
	 * return false if dto is already enabled or null, otherwise enables dto and
	 * returns true
	 */	
	  @Override 
	  public boolean verify(String token) { 
		  User user =  userRepository.findByEmailVerificationToken(token);
		  if (user == null ||
	  user.isEnabled()) { 
			  return false; 
			  } else {
	  userRepository.enable(user.getId());
	  return true; 
	  } 
		  }
	 
}
