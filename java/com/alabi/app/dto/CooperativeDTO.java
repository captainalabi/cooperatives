package com.alabi.app.dto;

import java.util.List;

import com.alabi.app.entity.User;

import lombok.Data;

@Data
public class CooperativeDTO {

	private long id;	
	private byte [] logo;
	private String cooperativeName;
	private String cooperativeAddress;
	private String cooperativeEmail;
	private String cooperativePhoneNumber;
	private String cooperativeCACRegNumber;
	private List<User> users;
}
